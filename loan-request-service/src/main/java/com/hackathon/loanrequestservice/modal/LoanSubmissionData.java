package com.hackathon.loanrequestservice.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loan_request_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanSubmissionData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_request_id")
    private Long loanRequestId;
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "cust_email_id")
    private String customerEmailId;

    @Column(name = "cust_birth_date")
    private Date birthDate;
    @Column(name = "loan_amount")
    private Long loanAmount;
    @Column(name = "cust_salary_per_month")
    private Long salaryPerMonth;
    @Column(name = "visiting_type")
    private String visitingType;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_type")
    private String companyType;
    @Column(name = "company_rating")
    private int companyRating;

    @Column(name = "loan_requested_date")
    private Date loanRequestedDate;
}
