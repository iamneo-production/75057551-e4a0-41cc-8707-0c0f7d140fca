package com.hackathon.loanrequestservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="customer_loan_audit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanStatusAuditDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rec_id")
    private Long recId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "loan_Status")
    private String loanStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "creation_date")
    private Date creationDate;

}
