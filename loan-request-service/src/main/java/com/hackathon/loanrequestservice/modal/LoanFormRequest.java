package com.hackathon.loanrequestservice.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanFormRequest {
    @NotNull(message = "Customer ID is mandatory to process the loan.")
    private Long customerId;

    @Email(message = "Email format is not valid.")
    private String customerEmailId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date birthDate;
    private Long loanAmount;
    @Min(value = 35000, message ="Minimum Salary should be INR 35,000.")
    private Long salaryPerMonth;
    private String visitingType;

    private String companyName;
    private String companyType;

    private int companyRating;

    private String employmentType;
    private long creditScore;

}
