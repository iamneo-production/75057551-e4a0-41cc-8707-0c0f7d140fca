package com.hackathon.loanrequestservice.serviceImpl;

import com.hackathon.loanrequestservice.customException.LoanProcessingCustomException;
import com.hackathon.loanrequestservice.entity.CustomerLoanStatusAuditDAO;
import com.hackathon.loanrequestservice.modal.LoanFormRequest;
import com.hackathon.loanrequestservice.modal.LoanSubmissionData;
import com.hackathon.loanrequestservice.repository.CustomerLoanStatusAuditRepo;
import com.hackathon.loanrequestservice.repository.LoanRequestProcessRepo;
import com.hackathon.loanrequestservice.service.LoanRequestProcessService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@Service
@Log4j2
public class LoanRequestProcessServiceImpl implements LoanRequestProcessService{

    @Autowired
    LoanRequestProcessRepo loanRequestProcessRepo;

    @Autowired
    CustomerLoanStatusAuditRepo customerLoanStatusAuditRepo;

    @Override
    public LoanSubmissionData LoanRequestProcess(LoanFormRequest loanFormRequest) throws LoanProcessingCustomException{
        try{
            log.info("loanFormRequest is: {} ",loanFormRequest);
            if(null != loanFormRequest && null != loanFormRequest.getBirthDate()){
                long difference_In_Years
                        = (new Date().getTime() - loanFormRequest.getBirthDate().getTime())
                        / (1000l * 60 * 60 * 24 * 365);
                System.out.println("*** Customer Age in Years: "+ difference_In_Years);
                if(difference_In_Years <= 17){
                    log.info("Customer age {} is less than the 18 years.",difference_In_Years);
                    throw new LoanProcessingCustomException("Age is not sufficient","NOT_ELIGIBLE");
                }
            }
            LoanSubmissionData loanSubmissionData = LoanSubmissionData.builder()
                    .customerId(loanFormRequest.getCustomerId())
                    .customerEmailId(loanFormRequest.getCustomerEmailId())
                    .birthDate(loanFormRequest.getBirthDate())
                    .loanAmount(loanFormRequest.getLoanAmount())
                    .salaryPerMonth(loanFormRequest.getSalaryPerMonth())
                    .visitingType(loanFormRequest.getVisitingType())
                    .companyName(loanFormRequest.getCompanyName())
                    .companyType(loanFormRequest.getCompanyType())
                    .companyRating(loanFormRequest.getCompanyRating())
                    .loanRequestedDate(new Date())
                    .creditScore(loanFormRequest.getCreditScore())
                    .employmentType(loanFormRequest.getEmploymentType())
                    .build();

            loanRequestProcessRepo.save(loanSubmissionData);
            log.info("Loan submitted successfully for customer {} with loan Id {}",loanSubmissionData.getCustomerId(),loanSubmissionData.getLoanRequestId());
            auditLoanStatus(loanSubmissionData.getCustomerId(), loanSubmissionData.getLoanRequestId(),"SUBMITTED",null);
            return loanSubmissionData;
        }catch(LoanProcessingCustomException ex){
            log.error("Customer {} is not meeting age criteria.", loanFormRequest.getCustomerId());
            auditLoanStatus(loanFormRequest.getCustomerId(), null,"REJECTED","Customer Age is less than required age(18) to take loan.");
            throw new LoanProcessingCustomException("Customer Age is less than required age(18) to take loan."
                    ,"LOAN_REQUEST_SUBMISSION_UNSUCCESSFUL");
        }catch(Exception e){
            e.printStackTrace();
            auditLoanStatus(loanFormRequest.getCustomerId(), null,"HOLD","System Error. Retry after sometime.");
            log.error("Error while processing the loan request submission for the customer {}",loanFormRequest.getCustomerId());
            throw new LoanProcessingCustomException("Loan Request submission is unsuccessful."
                    ,"LOAN_REQUEST_SUBMISSION_UNSUCCESSFUL");
        }

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
