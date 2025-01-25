package com.zigartiq.ledger.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @NotBlank
    private String accessToken;

    @NotBlank
    private String tokenType;
}
