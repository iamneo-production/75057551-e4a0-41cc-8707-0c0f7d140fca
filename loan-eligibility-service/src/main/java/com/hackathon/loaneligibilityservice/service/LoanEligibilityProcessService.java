package com.hackathon.loaneligibilityservice.service;

import com.hackathon.loaneligibilityservice.exceptions.LoanEligibilityCustomException;
import com.hackathon.loaneligibilityservice.entity.LoanSubmissionData;
import com.hackathon.loaneligibilityservice.modal.LoanEligibleDecisionVO;

public interface LoanEligibilityProcessService {

    LoanEligibleDecisionVO checkLoanEligibility(long customerId, long loanRequestId) throws LoanEligibilityCustomException;

    LoanSubmissionData getCustomerLoanRequestDetails(long customerId, long loanRequestId);
}
