package com.zigartiq.ledger.payload.response;

import com.zigartiq.ledger.utils.Constants.TransactionType;

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
public class TransactionResponse {
    @NotBlank
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private UUID categoryId;

    @NotBlank
    private String currency;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private TransactionType type;

    @NotNull
    private OffsetDateTime dateOfTransaction;

    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

    private String description;
}