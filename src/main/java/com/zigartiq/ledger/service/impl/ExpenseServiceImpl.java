package com.zigartiq.ledger.service.impl;

import com.zigartiq.ledger.entity.Expense;
import com.zigartiq.ledger.entity.User;
import com.zigartiq.ledger.payload.request.ExpenseRequest;
import com.zigartiq.ledger.payload.response.ExpenseResponse;
import com.zigartiq.ledger.repository.ExpenseRepository;
import com.zigartiq.ledger.repository.UserRepository;
import com.zigartiq.ledger.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseResponse addExpense(ExpenseRequest expenseRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense(
                user,
                expenseRequest.getName(),
                expenseRequest.getCategory(),
                expenseRequest.getCurrency(),
                expenseRequest.getAmount(),
                expenseRequest.getDateOfTransaction(),
                expenseRequest.getDescription());

        Expense savedExpense = expenseRepository.save(expense);

        return entityToResponse(savedExpense);
    }

    public List<ExpenseResponse> getCurrentUserExpenses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getUser().getId().equals(user.getId()))
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    private ExpenseResponse entityToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getName(),
                expense.getCategory(),
                expense.getCurrency(),
                expense.getAmount(),
                expense.getDateOfTransaction(),
                expense.getCreatedAt(),
                expense.getUpdatedAt(),
                expense.getDescription());
    }
}