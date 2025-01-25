package com.zigartiq.ledger.controller;

import com.zigartiq.ledger.payload.ApiResponse;
import com.zigartiq.ledger.payload.request.ExpenseRequest;
import com.zigartiq.ledger.payload.response.ExpenseResponse;
import com.zigartiq.ledger.service.ExpenseService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponse>> addExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        ExpenseResponse savedExpense = expenseService.addExpense(expenseRequest);

        return new ResponseEntity<>(new ApiResponse<>("success", "Expense added successfully", savedExpense),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getCurrentUserExpenses() {
        List<ExpenseResponse> expenses = expenseService.getCurrentUserExpenses();

        return new ResponseEntity<>(new ApiResponse<>("success", "", expenses), HttpStatus.OK);
    }

}
