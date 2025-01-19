package com.zigartiq.ledger.controller;

import com.zigartiq.ledger.payload.ApiResponse;
import com.zigartiq.ledger.payload.AuthResponse;
import com.zigartiq.ledger.payload.LoginDto;
import com.zigartiq.ledger.payload.RegisterDto;
import com.zigartiq.ledger.service.AuthService;

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
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterDto registerDto) {
        AuthResponse authResponseDto = authService.register(registerDto);

        return new ResponseEntity<>(new ApiResponse<>("success", "Registration successful", authResponseDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginDto loginDto) {
        AuthResponse authResponseDto = authService.login(loginDto);

        return new ResponseEntity<>(new ApiResponse<>("success", "", authResponseDto),
                HttpStatus.OK);
    }
}
