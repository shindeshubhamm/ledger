package com.zigartiq.ledger.service;

import com.zigartiq.ledger.dto.ExpenseDto;
import com.zigartiq.ledger.entity.Expense;
import com.zigartiq.ledger.entity.User;
import com.zigartiq.ledger.repository.ExpenseRepository;
import com.zigartiq.ledger.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        String username = "test";

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense(
                user,
                expenseDto.getName(),
                expenseDto.getCategory(),
                expenseDto.getCurrency(),
                expenseDto.getAmount(),
                expenseDto.getDescription());

        Expense savedExpense = expenseRepository.save(expense);

        return entityToDto(savedExpense);
    }

    public List<ExpenseDto> getCurrentUserExpenses() {
        String username = "test";

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getUser().getId().equals(user.getId()))
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private ExpenseDto entityToDto(Expense expense) {
        return new ExpenseDto(
                expense.getId(),
                expense.getName(),
                expense.getCategory(),
                expense.getCurrency(),
                expense.getAmount(),
                expense.getCreatedAt(),
                expense.getUpdatedAt(),
                expense.getDescription());
    }
}