package com.hackathon.decisionengineservice.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanEligibleDecisionVO {

    private Long customerId;
    private int riskFactor;
    private String decisionTakenByEngineProcess;
}
