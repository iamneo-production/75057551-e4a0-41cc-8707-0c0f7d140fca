package com.hackathon.loanrequestservice.service;

import com.hackathon.loanrequestservice.customException.LoanProcessingCustomException;
import com.hackathon.loanrequestservice.modal.LoanFormRequest;
import com.hackathon.loanrequestservice.modal.LoanSubmissionData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface LoanRequestProcessService {

    LoanSubmissionData LoanRequestProcess(LoanFormRequest loanFormRequest) throws LoanProcessingCustomException;
}
