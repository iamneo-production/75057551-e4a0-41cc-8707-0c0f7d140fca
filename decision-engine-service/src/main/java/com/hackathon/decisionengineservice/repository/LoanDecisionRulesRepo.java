package com.hackathon.decisionengineservice.repository;

import com.hackathon.decisionengineservice.entity.LoanDecisionRulesDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDecisionRulesRepo extends JpaRepository<LoanDecisionRulesDAO, Long> {
    List<LoanDecisionRulesDAO> findByEmploymentType(String employmentType);
}
