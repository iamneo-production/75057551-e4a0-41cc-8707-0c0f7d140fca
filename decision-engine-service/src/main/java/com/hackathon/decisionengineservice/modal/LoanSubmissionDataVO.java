package com.hackathon.decisionengineservice.modal;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanSubmissionDataVO {

    private Long loanRequestId;
    private Long customerId;
    private String customerEmailId;
    private Date birthDate;
    private Long loanAmount;
    private Long salaryPerMonth;
    private String visitingType;
    private String companyName;
    private String companyType;
    private int companyRating;
    private Date loanRequestedDate;
    private long creditScore;
    private String employmentType;
}
