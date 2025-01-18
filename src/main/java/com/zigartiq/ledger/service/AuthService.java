package com.zigartiq.ledger.service;

import com.zigartiq.ledger.entity.User;
import com.zigartiq.ledger.payload.AuthResponseDto;
import com.zigartiq.ledger.payload.LoginDto;
import com.zigartiq.ledger.payload.RegisterDto;
import com.zigartiq.ledger.repository.UserRepository;
import com.zigartiq.ledger.security.JWTService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthResponseDto register(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        return new AuthResponseDto();
    }

    public AuthResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        String token = jwtService.generateToken(authentication);
        return new AuthResponseDto(token, "Bearer");
    }
}
