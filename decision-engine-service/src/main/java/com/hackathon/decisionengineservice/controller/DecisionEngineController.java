package com.hackathon.decisionengineservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decisionprocess")
public class DecisionEngineController {

    @PostMapping
    public void checkLoanEligibility(){

    }
}
