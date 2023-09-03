package com.hackathon.loaneligibilityservice.repository;

import com.hackathon.loaneligibilityservice.entity.LoanDocumentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDocDetailsRepository extends JpaRepository<LoanDocumentDetails, String> {

    List<LoanDocumentDetails> findByCustomerAndLoanId(Long customerId, Long loanRequestId);
}
