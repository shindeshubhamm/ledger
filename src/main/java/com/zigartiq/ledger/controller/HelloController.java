package com.zigartiq.ledger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zigartiq.ledger.dto.ApiResponse;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return new ApiResponse<>("Welcome to Ledger API!", "Success");
    }
}
