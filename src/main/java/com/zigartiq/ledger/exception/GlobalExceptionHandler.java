package com.zigartiq.ledger.exception;

import com.zigartiq.ledger.payload.ApiResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException exception,
            WebRequest webRequest) {

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", exception.getMessage(), null, null,
                webRequest.getDescription(false));

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // Ledger Api Exception
    @ExceptionHandler(LedgerApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleLedgerApiException(LedgerApiException exception,
            WebRequest request) {

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", exception.getMessage(), null, null,
                request.getDescription(false));

        return new ResponseEntity<>(apiResponse, exception.getStatus());
    }

    // Global Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception exception, WebRequest request) {

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", exception.getMessage(), null, null,
                request.getDescription(false));

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Validation Exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", "Validation Errors", null, null, errors);

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    // Access Denied Exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException exception,
            WebRequest request) {

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", exception.getMessage(), null, null,
                request.getDescription(false));

        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    // Bad Credentials Exception
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException exception,
            WebRequest request) {

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", "Invalid username or password", null, null,
                request.getDescription(false));

        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    // No Handler Found Exception
    // Required in case of permitAll url is not found
    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", "Resource not found", null, null,
                request.getDescription(false));

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // Method Not Allowed Exception
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiResponse<Void> apiResponse = new ApiResponse<>("fail", "Method not allowed", null, null,
                request.getDescription(false));

        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
