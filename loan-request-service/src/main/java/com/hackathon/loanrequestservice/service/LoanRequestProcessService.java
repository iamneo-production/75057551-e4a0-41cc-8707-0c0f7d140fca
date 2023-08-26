package com.hackathon.loanrequestservice.service;

import com.hackathon.loanrequestservice.modal.LoanFormRequest;
import org.springframework.stereotype.Service;

@Service
public interface LoanRequestProcessService {

    void LoanRequestProcess(LoanFormRequest loanFormRequest);
}
