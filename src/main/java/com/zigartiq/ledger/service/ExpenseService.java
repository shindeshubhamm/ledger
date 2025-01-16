package com.zigartiq.ledger.service;

import com.zigartiq.ledger.dto.ExpenseDto;
import com.zigartiq.ledger.entity.Expense;
import com.zigartiq.ledger.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        Expense expense = DtoToEntity(expenseDto);
        Expense savedExpense = expenseRepository.save(expense);
        ExpenseDto savedDto = entityToDto(savedExpense);

        return savedDto;
    }

    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private Expense DtoToEntity(ExpenseDto expenseDto) {
        return new Expense(
                expenseDto.getName(),
                expenseDto.getCategory(),
                expenseDto.getCurrency(),
                expenseDto.getAmount(),
                expenseDto.getDescription());
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