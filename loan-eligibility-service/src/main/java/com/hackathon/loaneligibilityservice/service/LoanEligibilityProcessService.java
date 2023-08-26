package com.hackathon.loaneligibilityservice.service;

import com.hackathon.loaneligibilityservice.exceptions.LoanEligibilityCustomException;
import com.hackathon.loaneligibilityservice.modal.EligibilityResponseVO;
import com.hackathon.loaneligibilityservice.modal.LoanSubmissionData;

public interface LoanEligibilityProcessService {

    EligibilityResponseVO checkLoanEligibility(long customerId, long loanRequestId) throws LoanEligibilityCustomException;

    LoanSubmissionData getCustomerLoanRequestDetails(long customerId, long loanRequestId);
}
