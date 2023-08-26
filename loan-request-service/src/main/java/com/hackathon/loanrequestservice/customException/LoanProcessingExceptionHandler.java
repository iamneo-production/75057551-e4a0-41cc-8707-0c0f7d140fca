package com.hackathon.loanrequestservice.customException;

import com.hackathon.loanrequestservice.modal.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class LoanProcessingExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(LoanProcessingCustomException.class)
    public ResponseEntity<ErrorMessage> depositNotFoundException(LoanProcessingCustomException ex){

        ErrorMessage errorVO = ErrorMessage.builder()
                .errCode(ex.getErrorCode())
                .errMessage(ex.getErrorMessage())
                .errDateTime(new Date())
                .build();

        return new ResponseEntity<>(errorVO, HttpStatus.OK);

    }
}
