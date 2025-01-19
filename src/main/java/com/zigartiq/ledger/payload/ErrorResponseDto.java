package com.zigartiq.ledger.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    @NotNull
    private Date timestamp;
    @NotNull
    private String message;
    @NotNull
    private String details;
}
