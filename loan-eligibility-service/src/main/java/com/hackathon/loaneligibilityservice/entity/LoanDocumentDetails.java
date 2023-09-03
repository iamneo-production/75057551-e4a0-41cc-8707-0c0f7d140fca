package com.hackathon.loaneligibilityservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "loan_document_details")
public class LoanDocumentDetails {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_data")
    @Lob
    private byte[] fileData;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "loan_id")
    private long loanId;

    public LoanDocumentDetails(String fileName, String fileType, byte[] fileData, long customerId, long loanId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
        this.customerId = customerId;
        this.loanId = loanId;
    }
}
