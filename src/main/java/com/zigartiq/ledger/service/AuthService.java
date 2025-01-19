package com.zigartiq.ledger.service;

import com.zigartiq.ledger.payload.AuthResponse;
import com.zigartiq.ledger.payload.LoginDto;
import com.zigartiq.ledger.payload.RegisterDto;

public interface AuthService {

    public AuthResponse register(RegisterDto registerDto);

    public AuthResponse login(LoginDto loginDto);

}
