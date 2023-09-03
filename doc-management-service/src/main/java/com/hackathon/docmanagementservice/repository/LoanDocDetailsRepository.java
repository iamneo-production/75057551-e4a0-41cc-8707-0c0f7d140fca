package com.hackathon.docmanagementservice.repository;

import com.hackathon.docmanagementservice.entity.LoanDocumentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDocDetailsRepository extends JpaRepository<LoanDocumentDetails, String> {
    //LoanDocumentDetails saveLoanDetails(LoanDocumentDetails loanDocumentDetails);
}
