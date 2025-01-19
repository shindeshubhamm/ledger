package com.zigartiq.ledger.exception;

import com.zigartiq.ledger.payload.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
            WebRequest webRequest) {
        ErrorResponse errorResponseDto = new ErrorResponse(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LedgerApiException.class)
    public ResponseEntity<ErrorResponse> handleLedgerApiException(LedgerApiException exception, WebRequest request) {

        ErrorResponse errorResponseDto = new ErrorResponse(new Date(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorResponseDto, exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, WebRequest request) {

        ErrorResponse errorResponseDto = new ErrorResponse(new Date(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        StringBuilder errorMessage = new StringBuilder();
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);

            if (errorMessage.length() > 0) {
                errorMessage.append("; ");
            }
            errorMessage.append(fieldName).append(": ").append(message);
        });

        ErrorResponse errorResponseDto = new ErrorResponse(new Date(), "Validation Errors",
                errorMessage.toString());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception,
            WebRequest request) {

        ErrorResponse errorResponseDto = new ErrorResponse(new Date(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorResponseDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
            BadCredentialsException exception,
            WebRequest request) {
        ErrorResponse errorResponseDto = new ErrorResponse(
                new Date(),
                "Invalid username or password",
                request.getDescription(false));

        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
    }

}
