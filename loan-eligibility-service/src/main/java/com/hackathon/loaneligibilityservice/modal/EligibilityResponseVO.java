package com.hackathon.loaneligibilityservice.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EligibilityResponseVO {

    private long customerId;
    private long loanRequestId;
    private String eligibleForLoan;

    private ErrorMessage errorMessage;

}
