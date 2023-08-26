package com.hackathon.loaneligibilityservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoanEligibilityCustomException extends RuntimeException{

    private String errorCode;
     public LoanEligibilityCustomException(String message, String errorCode){
         super(message);
         this.errorCode = errorCode;
     }

}
