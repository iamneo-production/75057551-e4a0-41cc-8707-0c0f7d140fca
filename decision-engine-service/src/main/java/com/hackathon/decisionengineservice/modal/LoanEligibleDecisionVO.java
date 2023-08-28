package com.hackathon.decisionengineservice.modal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanEligibleDecisionVO {

    private Long customerId;

    private Long loanReferenceId;
    private int riskFactor;
    private String decisionTakenByEngineProcess;

    private String message;

    private long eligibleLoanAmount;
}
