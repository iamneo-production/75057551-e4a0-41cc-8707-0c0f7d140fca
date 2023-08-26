package com.hackathon.loaneligibilityservice.exceptions;

import com.hackathon.loaneligibilityservice.modal.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class LoanEligibilityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoanEligibilityCustomException.class)
    public ResponseEntity<ErrorMessage> CustomerLoanDataNotFoundException(LoanEligibilityCustomException ex){

        ErrorMessage errorVO = ErrorMessage.builder()
                .errCode(ex.getErrorCode())
                .errMessage(ex.getMessage())
                .errDateTime(new Date())
                .build();

        return new ResponseEntity<>(errorVO, HttpStatus.NOT_FOUND);

    }
}
