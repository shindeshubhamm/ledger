package com.zigartiq.ledger.service;

import com.zigartiq.ledger.payload.request.ExpenseRequest;
import com.zigartiq.ledger.payload.response.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    public ExpenseResponse addExpense(ExpenseRequest expenseRequest);

    public List<ExpenseResponse> getCurrentUserExpenses();

}
