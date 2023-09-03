package com.hackathon.docmanagementservice.service;

import com.hackathon.docmanagementservice.entity.LoanDocumentDetails;
import org.springframework.web.multipart.MultipartFile;

public interface LoanDocManagementService {

    LoanDocumentDetails saveFileData(MultipartFile file, long customerId, long loanId);

    LoanDocumentDetails getLoanDocuments(String fileId) throws Exception;
}
