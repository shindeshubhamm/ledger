package com.zigartiq.ledger.controller;

import com.zigartiq.ledger.payload.ExpenseDto;
import com.zigartiq.ledger.service.ExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto savedExpense = expenseService.addExpense(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getCurrentUserExpenses() {
        List<ExpenseDto> expenses = expenseService.getCurrentUserExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

}
