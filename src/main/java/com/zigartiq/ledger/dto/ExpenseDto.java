package com.zigartiq.ledger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private UUID id;
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
    private Date createdAt;
    private Date updatedAt;
    private String description;
}