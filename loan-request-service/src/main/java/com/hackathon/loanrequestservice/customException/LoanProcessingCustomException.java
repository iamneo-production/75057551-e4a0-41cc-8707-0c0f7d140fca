package com.hackathon.loanrequestservice.customException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoanProcessingCustomException extends RuntimeException{

    private String errCode;
    private String errMessage;

    public LoanProcessingCustomException(String errMessage, String errorCode){
            this.errMessage = errMessage;
            this.errCode = errorCode;
    }

}
