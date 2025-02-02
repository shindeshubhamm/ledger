package com.zigartiq.ledger.service.impl;

import com.zigartiq.ledger.entity.Category;
import com.zigartiq.ledger.entity.Transaction;
import com.zigartiq.ledger.entity.User;
import com.zigartiq.ledger.payload.request.TransactionRequest;
import com.zigartiq.ledger.payload.response.TransactionResponse;
import com.zigartiq.ledger.repository.CategoryRepository;
import com.zigartiq.ledger.repository.TransactionRepository;
import com.zigartiq.ledger.repository.UserRepository;
import com.zigartiq.ledger.service.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionResponse addTransaction(TransactionRequest transactionRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findByIdAndUserId(transactionRequest.getCategoryId(), user.getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction(
                user,
                transactionRequest.getName(),
                category,
                transactionRequest.getCurrency(),
                transactionRequest.getAmount(),
                transactionRequest.getType(),
                transactionRequest.getDateOfTransaction(),
                transactionRequest.getDescription());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return entityToResponse(savedTransaction);
    }

    public List<TransactionResponse> getTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getUser().getId().equals(user.getId()))
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse entityToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getName(),
                transaction.getCategory().getId(),
                transaction.getCurrency(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDateOfTransaction(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt(),
                transaction.getDescription());
    }
}