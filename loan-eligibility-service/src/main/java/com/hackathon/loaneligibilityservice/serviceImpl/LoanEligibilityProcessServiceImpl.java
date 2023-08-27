package com.hackathon.loaneligibilityservice.serviceImpl;

import com.hackathon.loaneligibilityservice.exceptions.LoanEligibilityCustomException;
import com.hackathon.loaneligibilityservice.external.client.DecisionServiceExternal;
import com.hackathon.loaneligibilityservice.entity.LoanSubmissionData;
import com.hackathon.loaneligibilityservice.modal.LoanEligibleDecisionVO;
import com.hackathon.loaneligibilityservice.modal.LoanSubmissionDataVO;
import com.hackathon.loaneligibilityservice.repository.CustomerLoanDetailsRepo;
import com.hackathon.loaneligibilityservice.service.LoanEligibilityProcessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityProcessServiceImpl implements LoanEligibilityProcessService {

    @Autowired
    CustomerLoanDetailsRepo customerLoanDetailsRepo;

    @Autowired
    DecisionServiceExternal decisionServiceExternal;

    @Override
    public LoanEligibleDecisionVO checkLoanEligibility(long customerId, long loanRequestId) throws LoanEligibilityCustomException {
        System.out.println("*** Request Params: "+ customerId+" :: "+loanRequestId);
        LoanSubmissionData loanSubmissionDataDAO = getCustomerLoanRequestDetails(customerId, loanRequestId);
        LoanSubmissionDataVO loanSubmissionDataVO = new LoanSubmissionDataVO();
        LoanEligibleDecisionVO loanEligibleDecisionVO = null;
        if(loanSubmissionDataDAO == null){
            throw new LoanEligibilityCustomException(
                    "Data not available with given Customer Id and Loan Id","DATA_NOT_FOUND");
        }else{
            BeanUtils.copyProperties(loanSubmissionDataDAO, loanSubmissionDataVO);
            System.out.println("loanSubmissionData is: "+ loanSubmissionDataVO);
            boolean isPreEligibilityCriteriaPass = preChecksForLoanEligibility(loanSubmissionDataVO);
            if(isPreEligibilityCriteriaPass){
                loanEligibleDecisionVO = decisionServiceExternal.processDecisionMaking(loanSubmissionDataVO);
                System.out.println("*** Response from Engine: "+ loanEligibleDecisionVO);
                if(null == loanEligibleDecisionVO){
                    loanEligibleDecisionVO.setLoanReferenceId(loanSubmissionDataVO.getLoanRequestId());
                    loanEligibleDecisionVO.setCustomerId(loanSubmissionDataVO.getCustomerId());
                    loanEligibleDecisionVO.setMessage("Error while processing loan decision, please proceed with manual loan process.");
                }
            }
        }
        return loanEligibleDecisionVO;
    }

    @Override
    public LoanSubmissionData getCustomerLoanRequestDetails(long customerId, long loanRequestId) {
        System.out.println("*** Before Repo Request Params: "+ customerId+" :: "+loanRequestId);
        LoanSubmissionData loanSubmissionData =
                customerLoanDetailsRepo.findByCustomerIdAndLoanRequestId(customerId, loanRequestId);
        System.out.println("*** Response from DB: "+ loanSubmissionData);
        return loanSubmissionData;
    }

    private boolean preChecksForLoanEligibility(LoanSubmissionDataVO loanSubmissionDataVO){

        if(!StringUtils.equalsIgnoreCase("SELF", loanSubmissionDataVO.getEmploymentType())
                && StringUtils.isEmpty(loanSubmissionDataVO.getCompanyName())){
            throw new LoanEligibilityCustomException(
                    "Company Name is required if customer is not self-employed.","COMPANY_NAME_REQUIRED");
        }
        return true;
    }

}
