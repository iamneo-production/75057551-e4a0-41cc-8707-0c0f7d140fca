package com.hackathon.loanrequestservice.repository;

import com.hackathon.loanrequestservice.modal.LoanSubmissionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestProcessRepo extends JpaRepository<LoanSubmissionData, Long> {
}
