package com.hackathon.loaneligibilityservice.controller;

import com.hackathon.loaneligibilityservice.exceptions.LoanEligibilityCustomException;
import com.hackathon.loaneligibilityservice.modal.EligibilityResponseVO;
import com.hackathon.loaneligibilityservice.serviceImpl.LoanEligibilityProcessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan/eligibility")
public class LoanEligibilityController {

    @Autowired
    LoanEligibilityProcessServiceImpl loanEligibilityProcessServiceImpl;

    @GetMapping("/{customerId}/{loanRequestId}")
    public ResponseEntity<EligibilityResponseVO> checkLoanEligibility(@PathVariable("customerId") long customerId,
                                                                      @PathVariable("loanRequestId") long loanRequestId)
            throws LoanEligibilityCustomException {
        EligibilityResponseVO eligibilityResponseVO = loanEligibilityProcessServiceImpl.checkLoanEligibility(customerId, loanRequestId);
        return new ResponseEntity<>(eligibilityResponseVO, HttpStatus.OK);
    }
}
