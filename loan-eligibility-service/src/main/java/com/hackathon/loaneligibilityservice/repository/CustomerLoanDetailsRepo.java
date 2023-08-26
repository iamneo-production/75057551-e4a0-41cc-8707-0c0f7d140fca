package com.hackathon.loaneligibilityservice.repository;

import com.hackathon.loaneligibilityservice.modal.LoanSubmissionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerLoanDetailsRepo extends JpaRepository<LoanSubmissionData, Long> {
    LoanSubmissionData findByCustomerIdAndLoanRequestId(long customerId, long loanRequestId);
}
