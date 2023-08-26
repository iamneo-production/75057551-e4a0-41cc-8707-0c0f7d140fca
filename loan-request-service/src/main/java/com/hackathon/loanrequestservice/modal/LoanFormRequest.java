package com.hackathon.loanrequestservice.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @NotBlank(message = "Company name is mandatory field.")
    private String companyName;
    private String companyType;
    @NotNull(message = "Company Rating value is mandatory field.")
    private int companyRating;

}
