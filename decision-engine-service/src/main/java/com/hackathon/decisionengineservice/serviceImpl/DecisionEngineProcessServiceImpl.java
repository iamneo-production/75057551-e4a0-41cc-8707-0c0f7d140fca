package com.hackathon.decisionengineservice.serviceImpl;

import com.hackathon.decisionengineservice.entity.LoanDecisionRulesDAO;
import com.hackathon.decisionengineservice.modal.LoanEligibleDecisionVO;
import com.hackathon.decisionengineservice.modal.LoanSubmissionDataVO;
import com.hackathon.decisionengineservice.repository.LoanDecisionRulesRepo;
import com.hackathon.decisionengineservice.service.DecisionEngineProcessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Service
public class DecisionEngineProcessServiceImpl implements DecisionEngineProcessService {

    @Autowired
    LoanDecisionRulesRepo loanDecisionRulesRepo;
    @Override
    public LoanEligibleDecisionVO executeDecisionBusinessRules(LoanSubmissionDataVO loanSubmissionDataVO) {
        LoanEligibleDecisionVO loanEligibleDecisionVO = new LoanEligibleDecisionVO();
        if(null != loanSubmissionDataVO){
            loanEligibleDecisionVO.setCustomerId(loanSubmissionDataVO.getCustomerId());
            loanEligibleDecisionVO.setLoanReferenceId(loanSubmissionDataVO.getLoanRequestId());

            if(StringUtils.equalsIgnoreCase("SELF",loanSubmissionDataVO.getEmploymentType())){
                List<LoanDecisionRulesDAO> decisionRules = getDecisionRules(loanSubmissionDataVO.getEmploymentType());
                runDecisionEngineForSelfEmployedCustomers(loanEligibleDecisionVO, loanSubmissionDataVO,decisionRules);
            }
        }
        return loanEligibleDecisionVO;
    }

    public void runDecisionEngineForSelfEmployedCustomers(LoanEligibleDecisionVO loanEligibleDecisionVO, LoanSubmissionDataVO loanSubmissionDataVO,
                                                          List<LoanDecisionRulesDAO> decisionRules){
        if(CollectionUtils.isEmpty(decisionRules)){
            loanEligibleDecisionVO.setMessage("Decision Rules are not configured. Please proceed with manual process.");
        }else{
            long maxLoanAmountApplicable =  decisionRules.stream().mapToLong(e -> e.getMaxLoanAmount()).max().orElse(0);
            if(loanSubmissionDataVO.getLoanAmount() > maxLoanAmountApplicable){
                loanEligibleDecisionVO.setMessage("Requested Loan amount "+ loanSubmissionDataVO.getLoanAmount() + " is exceed max applicable loan amount: "+maxLoanAmountApplicable);
                loanEligibleDecisionVO.setEligibleLoanAmount(maxLoanAmountApplicable);
            }else {
                List<LoanDecisionRulesDAO> decisionRuleRecords = decisionRules.stream()
                        .filter(e -> loanSubmissionDataVO.getCreditScore() >= e.getCreditScoreFrom()
                                && loanSubmissionDataVO.getCreditScore() <= e.getCreditScoreTo())
                        .collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(decisionRuleRecords)){
                    long minSalary = decisionRuleRecords.stream().mapToLong(e -> e.getMinSalary()).min().orElse(0);
                    long maxSalary = decisionRuleRecords.stream().mapToLong(e -> e.getMinSalary()).max().orElse(0);

                    if(loanSubmissionDataVO.getSalaryPerMonth() < minSalary){
                        loanEligibleDecisionVO.setMessage("Customer Salary is not meeting the minimum salary criteria.");
                        loanEligibleDecisionVO.setDecisionTakenByEngineProcess("NOT_ELIGIBLE");
                    }else if(loanSubmissionDataVO.getSalaryPerMonth() <= maxSalary){
                        LoanDecisionRulesDAO decisionRecordWithSalary = decisionRuleRecords.stream()
                                .filter(e -> loanSubmissionDataVO.getSalaryPerMonth() >= e.getMinSalary()
                                        && loanSubmissionDataVO.getSalaryPerMonth() <= e.getMaxSalary())
                                .findFirst().orElse(null);
                        if(null != decisionRecordWithSalary){
                            loanEligibleDecisionVO.setRiskFactor((int)decisionRecordWithSalary.getRiskFactor());
                            loanEligibleDecisionVO.setEligibleLoanAmount(
                                    loanSubmissionDataVO.getLoanAmount() >= decisionRecordWithSalary.getMaxLoanAmount() ?
                                            decisionRecordWithSalary.getMaxLoanAmount() :  loanSubmissionDataVO.getLoanAmount());
                            loanEligibleDecisionVO.setMessage(
                                    loanSubmissionDataVO.getLoanAmount() > decisionRecordWithSalary.getMaxLoanAmount() ?
                                            "You are eligible for max loan amount Rs."+decisionRecordWithSalary.getMaxLoanAmount(): null);
                            loanEligibleDecisionVO.setDecisionTakenByEngineProcess("ELIGIBLE");
                        }

                    }else if(loanSubmissionDataVO.getSalaryPerMonth() > maxSalary){
                        loanEligibleDecisionVO.setMessage("Customer salary is more than the defined amount in given credit score.Please proceed with manual process.");
                        loanEligibleDecisionVO.setDecisionTakenByEngineProcess("ELIGIBLE");
                    }
                }else{
                    loanEligibleDecisionVO.setMessage("Decision rule is not available for given combination. Please proceed with manual process.");
                }
            }
        }

    }
    public List<LoanDecisionRulesDAO> getDecisionRules(String employmentType){
        return loanDecisionRulesRepo.findByEmploymentType(employmentType);
    }
}
