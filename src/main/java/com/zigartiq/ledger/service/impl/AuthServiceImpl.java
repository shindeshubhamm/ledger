package com.zigartiq.ledger.service.impl;

import com.zigartiq.ledger.entity.User;
import com.zigartiq.ledger.exception.LedgerApiException;
import com.zigartiq.ledger.payload.AuthResponseDto;
import com.zigartiq.ledger.payload.LoginDto;
import com.zigartiq.ledger.payload.RegisterDto;
import com.zigartiq.ledger.repository.UserRepository;
import com.zigartiq.ledger.security.JWTService;
import com.zigartiq.ledger.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    private String generateToken(String usernameOrEmail, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

        return jwtService.generateToken(authentication);
    }

    public AuthResponseDto register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new LedgerApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new LedgerApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new LedgerApiException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        String token = generateToken(registerDto.getUsername(), registerDto.getPassword());

        return new AuthResponseDto(token, "Bearer");
    }

    public AuthResponseDto login(LoginDto loginDto) {

        String token = generateToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        return new AuthResponseDto(token, "Bearer");
    }
}
