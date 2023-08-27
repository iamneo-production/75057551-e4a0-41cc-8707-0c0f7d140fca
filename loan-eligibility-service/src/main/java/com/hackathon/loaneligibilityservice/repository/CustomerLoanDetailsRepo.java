package com.hackathon.loaneligibilityservice.repository;

import com.hackathon.loaneligibilityservice.entity.LoanSubmissionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLoanDetailsRepo extends JpaRepository<LoanSubmissionData, Long> {

    //@Query(value = "select t.* from loan_request_details t where t.customer_id = ?1 and t.loan_request_id = ?2",nativeQuery = true)
    //LoanSubmissionData findByCustomerIdAndLoanRequestId(long customerId, long loanRequestId);

    LoanSubmissionData findByCustomerIdAndLoanRequestId(long customerId, long loanRequestId);

}
