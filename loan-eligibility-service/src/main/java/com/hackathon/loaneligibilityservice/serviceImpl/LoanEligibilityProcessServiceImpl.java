package com.hackathon.loaneligibilityservice.serviceImpl;

import com.hackathon.loaneligibilityservice.entity.CustomerLoanStatusAuditDAO;
import com.hackathon.loaneligibilityservice.entity.LoanDocumentDetails;
import com.hackathon.loaneligibilityservice.exceptions.LoanEligibilityCustomException;
import com.hackathon.loaneligibilityservice.external.client.DecisionServiceExternal;
import com.hackathon.loaneligibilityservice.entity.LoanSubmissionData;
import com.hackathon.loaneligibilityservice.modal.LoanEligibleDecisionVO;
import com.hackathon.loaneligibilityservice.modal.LoanSubmissionDataVO;
import com.hackathon.loaneligibilityservice.repository.CustomerLoanDetailsRepo;
import com.hackathon.loaneligibilityservice.repository.CustomerLoanStatusAuditRepo;
import com.hackathon.loaneligibilityservice.repository.LoanDocDetailsRepository;
import com.hackathon.loaneligibilityservice.service.LoanEligibilityProcessService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class LoanEligibilityProcessServiceImpl implements LoanEligibilityProcessService {

    @Autowired
    CustomerLoanDetailsRepo customerLoanDetailsRepo;

    @Autowired
    DecisionServiceExternal decisionServiceExternal;

    @Autowired
    CustomerLoanStatusAuditRepo customerLoanStatusAuditRepo;

    @Autowired
    LoanDocDetailsRepository loanDocDetailsRepository;

    @Override
    public LoanEligibleDecisionVO checkLoanEligibility(long customerId, long loanRequestId) throws LoanEligibilityCustomException {
        log.info("Checking loan eligibility for customer {} with loan Id {}.",customerId, loanRequestId);
        LoanSubmissionData loanSubmissionDataDAO = getCustomerLoanRequestDetails(customerId, loanRequestId);
        LoanSubmissionDataVO loanSubmissionDataVO = new LoanSubmissionDataVO();
        LoanEligibleDecisionVO loanEligibleDecisionVO = null;
        if(loanSubmissionDataDAO == null){
            log.info("No details are found from records for customer {} with loan Id {}.",customerId, loanRequestId);
            throw new LoanEligibilityCustomException(
                    "Data not available with given Customer Id and Loan Id","DATA_NOT_FOUND");
        }else{
            BeanUtils.copyProperties(loanSubmissionDataDAO, loanSubmissionDataVO);
            log.info("loanSubmissionData is: {}", loanSubmissionDataVO);
            boolean isPreEligibilityCriteriaPass = preChecksForLoanEligibility(loanSubmissionDataVO);
            log.info("Status of pre check criteria for loan eligibility: {} ", isPreEligibilityCriteriaPass);
            if(isPreEligibilityCriteriaPass){
                loanEligibleDecisionVO = decisionServiceExternal.processDecisionMaking(loanSubmissionDataVO);
                log.info("Response from decision Engine: {} ", loanEligibleDecisionVO);

                if(null == loanEligibleDecisionVO){
                    loanEligibleDecisionVO.setLoanReferenceId(loanSubmissionDataVO.getLoanRequestId());
                    loanEligibleDecisionVO.setCustomerId(loanSubmissionDataVO.getCustomerId());
                    loanEligibleDecisionVO.setDecisionTakenByEngineProcess("HOLD");
                    loanEligibleDecisionVO.setMessage("Error while processing loan decision, please proceed with manual loan process.");

                }
                auditLoanStatus(loanEligibleDecisionVO.getCustomerId(),loanEligibleDecisionVO.getLoanReferenceId(),
                        loanEligibleDecisionVO.getDecisionTakenByEngineProcess(), loanEligibleDecisionVO.getMessage());
            }
        }
        return loanEligibleDecisionVO;
    }

    @Override
    public LoanSubmissionData getCustomerLoanRequestDetails(long customerId, long loanRequestId) {
        LoanSubmissionData loanSubmissionData =
                customerLoanDetailsRepo.findByCustomerIdAndLoanRequestId(customerId, loanRequestId);
        log.info("Details from database : {}", loanSubmissionData);
        return loanSubmissionData;
    }

    private boolean preChecksForLoanEligibility(LoanSubmissionDataVO loanSubmissionDataVO){

        if(!StringUtils.equalsIgnoreCase("SELF", loanSubmissionDataVO.getEmploymentType())
                && StringUtils.isEmpty(loanSubmissionDataVO.getCompanyName())){
            log.info("Organisation name is mandatory for salaried customers. Customer id: {}", loanSubmissionDataVO.getCustomerId());

            LoanEligibleDecisionVO loanEligibleDecisionVO = new LoanEligibleDecisionVO().builder()
                    .loanReferenceId(loanSubmissionDataVO.getCustomerId())
                    .customerId(loanSubmissionDataVO.getLoanRequestId())
                    .decisionTakenByEngineProcess("HOLD")
                    .message("Company Name is required if customer is not self-employed.")
                    .build();
            auditLoanStatus(loanEligibleDecisionVO.getCustomerId(),loanEligibleDecisionVO.getLoanReferenceId(),
                    loanEligibleDecisionVO.getDecisionTakenByEngineProcess(), loanEligibleDecisionVO.getMessage());
            throw new LoanEligibilityCustomException(
                    "Company Name is required if customer is not self-employed.","COMPANY_NAME_REQUIRED");
        }
        List<LoanDocumentDetails> loanDocumentDetails = loanDocDetailsRepository.findByCustomerAndLoanId(
                loanSubmissionDataVO.getCustomerId(), loanSubmissionDataVO.getLoanRequestId());
        if(CollectionUtils.isEmpty(loanDocumentDetails)){
            LoanEligibleDecisionVO loanEligibleDecisionVO = new LoanEligibleDecisionVO().builder()
                    .loanReferenceId(loanSubmissionDataVO.getCustomerId())
                    .customerId(loanSubmissionDataVO.getLoanRequestId())
                    .decisionTakenByEngineProcess("HOLD")
                    .message("Documents are not provided.")
                    .build();
            auditLoanStatus(loanEligibleDecisionVO.getCustomerId(),loanEligibleDecisionVO.getLoanReferenceId(),
                    loanEligibleDecisionVO.getDecisionTakenByEngineProcess(), loanEligibleDecisionVO.getMessage());
            throw new LoanEligibilityCustomException(
                    "All Documents are not provided.","DOCUMENTS_NOT_PROVIDED");
        }
        return true;
    }

    private void  auditLoanStatus(Long customerId, Long loanId,String status, String remarks){
        CustomerLoanStatusAuditDAO customerLoanStatusAuditDAO =
                CustomerLoanStatusAuditDAO.builder()
                        .customerId(customerId)
                        .loanId(loanId)
                        .loanStatus(status)
                        .remarks(remarks)
                        .creationDate(new Date())
                        .build();
        customerLoanStatusAuditRepo.save(customerLoanStatusAuditDAO);

    }

}
