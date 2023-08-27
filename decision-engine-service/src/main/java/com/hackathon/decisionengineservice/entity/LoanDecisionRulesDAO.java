package com.hackathon.decisionengineservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loan_decision_rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDecisionRulesDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rule_id")
    private long ruleId;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "credit_score_from")
    private long creditScoreFrom;

    @Column(name = "credit_score_to")
    private long creditScoreTo;

    @Column(name = "min_salary")
    private long minSalary;

    @Column(name = "max_salary")
    private long maxSalary;

    @Column(name = "max_loan_amount")
    private long maxLoanAmount;

    @Column(name = "risk_factor")
    private long riskFactor;

    @Column(name = "rule_creation_date")
    private Date ruleCreationDate;

    @Column(name = "rule_modified_date")
    private Date ruleModifiedDate;

}
