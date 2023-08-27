package com.hackathon.loaneligibilityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoanEligibilityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanEligibilityServiceApplication.class, args);
	}

}
