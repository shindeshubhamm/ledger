package com.zigartiq.ledger.service.impl;

import com.zigartiq.ledger.entity.Category;
import com.zigartiq.ledger.entity.User;
import com.zigartiq.ledger.exception.LedgerApiException;
import com.zigartiq.ledger.payload.request.LoginRequest;
import com.zigartiq.ledger.payload.request.RegisterRequest;
import com.zigartiq.ledger.payload.response.AuthResponse;
import com.zigartiq.ledger.repository.CategoryRepository;
import com.zigartiq.ledger.repository.UserRepository;
import com.zigartiq.ledger.security.JWTService;
import com.zigartiq.ledger.service.AuthService;
import com.zigartiq.ledger.utils.Constants;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final CategoryRepository categoryRepository;

    private String generateToken(String usernameOrEmail, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

        return jwtService.generateToken(authentication);
    }

    public AuthResponse register(RegisterRequest registerRequest) {

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new LedgerApiException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail().trim())) {
            throw new LedgerApiException(HttpStatus.CONFLICT, "Email already exists");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername().trim())) {
            throw new LedgerApiException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername().trim());
        user.setEmail(registerRequest.getEmail().trim());
        user.setFirstName(registerRequest.getFirstName().trim());
        user.setLastName(registerRequest.getLastName().trim());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);

        // Populate default categories for the new user
        categoryRepository.saveAll(
                Constants.DEFAULT_CATEGORIES.stream()
                        .map(category -> new Category(user, category))
                        .collect(Collectors.toList()));

        String token = generateToken(registerRequest.getUsername(), registerRequest.getPassword());

        return new AuthResponse(token, "Bearer");
    }

    public AuthResponse login(LoginRequest loginRequest) {

        String token = generateToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        return new AuthResponse(token, "Bearer");
    }
}
