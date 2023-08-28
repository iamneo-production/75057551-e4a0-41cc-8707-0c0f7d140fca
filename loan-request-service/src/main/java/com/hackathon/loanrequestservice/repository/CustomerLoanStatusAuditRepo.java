package com.hackathon.loanrequestservice.repository;

import com.hackathon.loanrequestservice.entity.CustomerLoanStatusAuditDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLoanStatusAuditRepo extends JpaRepository<CustomerLoanStatusAuditDAO, Long> {
}
