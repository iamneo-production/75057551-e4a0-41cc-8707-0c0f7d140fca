package com.hackathon.loaneligibilityservice.repository;

import com.hackathon.loaneligibilityservice.entity.CustomerLoanStatusAuditDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLoanStatusAuditRepo extends JpaRepository<CustomerLoanStatusAuditDAO, Long> {
}
