package com.hackathon.loanrequestservice.controller;

import com.hackathon.loanrequestservice.modal.LoanFormRequest;
import com.hackathon.loanrequestservice.serviceImpl.LoanRequestProcessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanRequestProcessController {

    @Autowired
    LoanRequestProcessServiceImpl loanRequestProcessServiceImpl;

    @PostMapping("/submit")
    public String submitLoanRequest(@RequestBody LoanFormRequest loanFormRequest){
        loanRequestProcessServiceImpl.LoanRequestProcess(loanFormRequest);
        return "Welcome Loans Dept ..!!";
    }
}
