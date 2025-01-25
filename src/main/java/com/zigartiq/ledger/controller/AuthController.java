package com.zigartiq.ledger.controller;

import com.zigartiq.ledger.payload.ApiResponse;
import com.zigartiq.ledger.payload.request.LoginRequest;
import com.zigartiq.ledger.payload.request.RegisterRequest;
import com.zigartiq.ledger.payload.response.AuthResponse;
import com.zigartiq.ledger.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);

        return new ResponseEntity<>(new ApiResponse<>("success", "Registration successful", authResponse),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);

        return new ResponseEntity<>(new ApiResponse<>("success", "", authResponse),
                HttpStatus.OK);
    }
}
