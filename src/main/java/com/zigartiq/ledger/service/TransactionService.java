package com.zigartiq.ledger.service;

import com.zigartiq.ledger.payload.request.TransactionRequest;
import com.zigartiq.ledger.payload.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    public TransactionResponse addTransaction(TransactionRequest transactionRequest);

    public List<TransactionResponse> getTransactions();

}
