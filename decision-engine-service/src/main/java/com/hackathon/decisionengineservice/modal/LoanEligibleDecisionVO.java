package com.hackathon.decisionengineservice.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanEligibleDecisionVO {

    private Long customerId;

    private Long loanReferenceId;
    private int riskFactor;
    private String decisionTakenByEngineProcess;

    private String message;

    private long eligibleLoanAmount;
}