package com.hackathon.loaneligibilityservice.serviceImpl;

import com.hackathon.loaneligibilityservice.exceptions.LoanEligibilityCustomException;
import com.hackathon.loaneligibilityservice.modal.EligibilityResponseVO;
import com.hackathon.loaneligibilityservice.modal.LoanSubmissionData;
import com.hackathon.loaneligibilityservice.repository.CustomerLoanDetailsRepo;
import com.hackathon.loaneligibilityservice.service.LoanEligibilityProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityProcessServiceImpl implements LoanEligibilityProcessService {

    @Autowired
    CustomerLoanDetailsRepo customerLoanDetailsRepo;

    @Override
    public EligibilityResponseVO checkLoanEligibility(long customerId, long loanRequestId) throws LoanEligibilityCustomException {
        LoanSubmissionData loanSubmissionData = getCustomerLoanRequestDetails(customerId, loanRequestId);
        if(loanSubmissionData == null){
            throw new LoanEligibilityCustomException(
                    "Data not available with given Customer Id and Loan Id","DATA_NOT_FOUND");
        }else{
            System.out.println("loanSubmissionData is: "+ loanSubmissionData);
            preChecksForLoanEligibility();
        }
        return new EligibilityResponseVO();
    }

    @Override
    public LoanSubmissionData getCustomerLoanRequestDetails(long customerId, long loanRequestId) {
        LoanSubmissionData loanSubmissionData =
                customerLoanDetailsRepo.findByCustomerIdAndLoanRequestId(customerId, loanRequestId);
        return loanSubmissionData;
    }

    private void preChecksForLoanEligibility(){

    }

}
