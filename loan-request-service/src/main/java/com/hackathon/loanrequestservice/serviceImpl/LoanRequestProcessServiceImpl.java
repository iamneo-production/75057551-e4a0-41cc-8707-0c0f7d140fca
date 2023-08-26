package com.hackathon.loanrequestservice.serviceImpl;

import com.hackathon.loanrequestservice.modal.LoanFormRequest;
import com.hackathon.loanrequestservice.modal.LoanSubmissionData;
import com.hackathon.loanrequestservice.repository.LoanRequestProcessRepo;
import com.hackathon.loanrequestservice.service.LoanRequestProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoanRequestProcessServiceImpl implements LoanRequestProcessService {

    @Autowired
    LoanRequestProcessRepo loanRequestProcessRepo;
    @Override
    public void LoanRequestProcess(LoanFormRequest loanFormRequest) {
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
                .build();

        loanRequestProcessRepo.save(loanSubmissionData);

    }
}