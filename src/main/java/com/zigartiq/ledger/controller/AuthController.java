package com.zigartiq.ledger.controller;

import com.zigartiq.ledger.payload.AuthResponseDto;
import com.zigartiq.ledger.payload.LoginDto;
import com.zigartiq.ledger.payload.RegisterDto;
import com.zigartiq.ledger.service.UserService;

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

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterDto registerDto) {
        AuthResponseDto authResponseDto = userService.register(registerDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        AuthResponseDto authResponseDto = userService.login(loginDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }
}
