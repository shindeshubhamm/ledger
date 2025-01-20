package com.zigartiq.ledger.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    @NotNull
    private String status; // success, fail, error

    @NotNull
    private String message;

    private T data = null;

    private Object meta = null;

    private Object errors = null;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String status, String message, T data, Object meta) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.meta = meta;
    }
}
