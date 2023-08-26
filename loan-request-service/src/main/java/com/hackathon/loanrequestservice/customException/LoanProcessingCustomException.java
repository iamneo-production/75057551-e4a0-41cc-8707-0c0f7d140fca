package com.hackathon.loanrequestservice.customException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanProcessingCustomException extends Exception{


    private String errorMessage;
    private String errorCode;
    private String customerId;
}
