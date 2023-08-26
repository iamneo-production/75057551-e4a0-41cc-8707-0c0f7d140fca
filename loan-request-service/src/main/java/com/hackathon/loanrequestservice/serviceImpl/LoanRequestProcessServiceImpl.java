package com.hackathon.loanrequestservice.serviceImpl;

import com.hackathon.loanrequestservice.customException.LoanProcessingCustomException;
import com.hackathon.loanrequestservice.modal.LoanFormRequest;
import com.hackathon.loanrequestservice.modal.LoanSubmissionData;
import com.hackathon.loanrequestservice.repository.LoanRequestProcessRepo;
import com.hackathon.loanrequestservice.service.LoanRequestProcessService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Log4j2
public class LoanRequestProcessServiceImpl implements LoanRequestProcessService{

    @Autowired
    LoanRequestProcessRepo loanRequestProcessRepo;

    @Override
    public LoanSubmissionData LoanRequestProcess(LoanFormRequest loanFormRequest) throws LoanProcessingCustomException{
        try{
            System.out.println("loanFormRequest is: "+loanFormRequest);
            if(null != loanFormRequest && null != loanFormRequest.getBirthDate()){
                long difference_In_Years
                        = (new Date().getTime() - loanFormRequest.getBirthDate().getTime())
                        / (1000l * 60 * 60 * 24 * 365);
                System.out.println("*** Customer Age in Years: "+ difference_In_Years);
                if(difference_In_Years <= 17){
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
            return loanSubmissionData;
        }catch(LoanProcessingCustomException ex){
            throw new LoanProcessingCustomException("Customer Age is less than required age(18) to take loan."
                    ,"LOAN_REQUEST_SUBMISSION_UNSUCCESSFUL");
        }catch(Exception e){
            e.printStackTrace();
            throw new LoanProcessingCustomException("Loan Request submission is unsuccessful."
                    ,"LOAN_REQUEST_SUBMISSION_UNSUCCESSFUL");
        }

    }
}
