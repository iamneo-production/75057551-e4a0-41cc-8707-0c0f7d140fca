package com.hackathon.loanrequestservice.controller;

import com.hackathon.loanrequestservice.customException.LoanProcessingCustomException;
import com.hackathon.loanrequestservice.modal.LoanFormRequest;
import com.hackathon.loanrequestservice.modal.LoanSubmissionData;
import com.hackathon.loanrequestservice.serviceImpl.LoanRequestProcessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/loan")
public class LoanRequestProcessController {

    @Autowired
    LoanRequestProcessServiceImpl loanRequestProcessServiceImpl;

    @PostMapping("/submit")
    public ResponseEntity<LoanSubmissionData> submitLoanRequest(@Valid @RequestBody LoanFormRequest loanFormRequest)
            throws LoanProcessingCustomException {
        LoanSubmissionData loanSubmissionData = loanRequestProcessServiceImpl.LoanRequestProcess(loanFormRequest);
        return new ResponseEntity<>(loanSubmissionData, HttpStatus.CREATED);
    }
}
