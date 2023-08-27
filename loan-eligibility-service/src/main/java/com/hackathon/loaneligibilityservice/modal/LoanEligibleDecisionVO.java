package com.hackathon.loaneligibilityservice.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanEligibleDecisionVO {

    private Long customerId;
    private int riskFactor;

    private Long loanReferenceId;
    private String decisionTakenByEngineProcess;

    private String message;

    private long eligibleLoanAmount;
}
