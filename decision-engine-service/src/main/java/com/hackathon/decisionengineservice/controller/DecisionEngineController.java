package com.hackathon.decisionengineservice.controller;

import com.hackathon.decisionengineservice.modal.LoanEligibleDecisionVO;
import com.hackathon.decisionengineservice.modal.LoanSubmissionDataVO;
import com.hackathon.decisionengineservice.serviceImpl.DecisionEngineProcessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decisionprocess")
public class DecisionEngineController {

    @Autowired
    DecisionEngineProcessServiceImpl decisionEngineProcessServiceImpl;

    @PostMapping
    public LoanEligibleDecisionVO processDecisionMaking(@RequestBody LoanSubmissionDataVO loanSubmissionDataVO){
        return decisionEngineProcessServiceImpl.executeDecisionBusinessRules(loanSubmissionDataVO);
    }
}
