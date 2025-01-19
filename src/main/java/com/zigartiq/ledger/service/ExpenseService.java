package com.zigartiq.ledger.service;

import com.zigartiq.ledger.payload.ExpenseDto;

import java.util.List;

public interface ExpenseService {

    public ExpenseDto addExpense(ExpenseDto expenseDto);

    public List<ExpenseDto> getCurrentUserExpenses();

    public List<ExpenseDto> getAllExpenses();
}
