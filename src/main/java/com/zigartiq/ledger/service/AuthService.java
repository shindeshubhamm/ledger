package com.zigartiq.ledger.service;

import com.zigartiq.ledger.payload.request.LoginRequest;
import com.zigartiq.ledger.payload.request.RegisterRequest;
import com.zigartiq.ledger.payload.response.AuthResponse;

public interface AuthService {

    public AuthResponse register(RegisterRequest registerRequest);

    public AuthResponse login(LoginRequest loginRequest);

}
