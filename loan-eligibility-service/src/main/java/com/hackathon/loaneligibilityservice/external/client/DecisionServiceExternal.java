package com.hackathon.loaneligibilityservice.external.client;

import com.hackathon.loaneligibilityservice.modal.LoanEligibleDecisionVO;
import com.hackathon.loaneligibilityservice.modal.LoanSubmissionDataVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DECISION-ENGINE-SERVICE/decisionprocess")
public interface DecisionServiceExternal {
    @PostMapping
    LoanEligibleDecisionVO processDecisionMaking(@RequestBody LoanSubmissionDataVO loanSubmissionDataVO);
}
