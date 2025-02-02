package com.zigartiq.ledger.controller;

import com.zigartiq.ledger.payload.StandardResponse;
import com.zigartiq.ledger.payload.request.TransactionRequest;
import com.zigartiq.ledger.payload.response.TransactionResponse;
import com.zigartiq.ledger.service.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transaction management APIs")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<StandardResponse<TransactionResponse>> addTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest) {

        TransactionResponse savedTransaction = transactionService.addTransaction(transactionRequest);

        return new ResponseEntity<>(
                new StandardResponse<>("success", "Transaction added successfully", savedTransaction),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<StandardResponse<List<TransactionResponse>>> getTransactions() {

        List<TransactionResponse> transactions = transactionService.getTransactions();

        return new ResponseEntity<>(new StandardResponse<>("success", "", transactions), HttpStatus.OK);
    }

}
