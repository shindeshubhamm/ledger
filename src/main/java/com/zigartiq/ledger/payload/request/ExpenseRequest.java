package com.zigartiq.ledger.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private String currency;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private OffsetDateTime dateOfTransaction;

    private String description;
}