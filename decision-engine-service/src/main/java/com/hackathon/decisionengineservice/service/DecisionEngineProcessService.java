package com.hackathon.decisionengineservice.service;

import com.hackathon.decisionengineservice.modal.LoanEligibleDecisionVO;
import com.hackathon.decisionengineservice.modal.LoanSubmissionDataVO;

public interface DecisionEngineProcessService {

    LoanEligibleDecisionVO executeDecisionBusinessRules(LoanSubmissionDataVO loanSubmissionDataVO);
}
