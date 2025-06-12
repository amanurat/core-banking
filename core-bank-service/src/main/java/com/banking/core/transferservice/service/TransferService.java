
package com.banking.core.transferservice.service;

import com.banking.core.account.entity.Account;
import com.banking.core.account.repository.AccountRepository;
import com.banking.core.transferservice.dto.TransferRequest;
import com.banking.core.transferservice.dto.TransferResponse;
import com.banking.core.transferservice.entity.Transaction;
import com.banking.core.transferservice.repository.TransactionRepository;
import com.banking.core.userservice.dto.UserDetail;
import com.banking.core.userservice.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final IdempotencyService idempotencyService;
    private final RedisTemplate<String, String> redisTemplate;

    // Helper methods
    private String createIdempotencyKey(TransferRequest request) {
        return String.format("txn:%s", request.getTransactionId());
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request, UserDetail authenticatedUser) {

        // Step 1: Create idempotency key
        String idempotencyKey = createIdempotencyKey(request);

        // Step 2: Check if we already processed this request
        // ตรวจสอบว่า Request นี้ซ้ำหรือไม่
        if (idempotencyService.isDuplicateRequest(idempotencyKey)) {
            log.warn("Duplicate request detected for transaction ID: {}", request.getTransactionId());
            throw new RuntimeException("Duplicate Request: Transaction already processed");
        }

        // Step 3: Check database for existing transaction
        Optional<Transaction> existingTransaction = transactionRepository.findByTransactionId(request.getTransactionId());

        if (existingTransaction.isPresent()) {
            // Transaction exists, then throw exception

            log.warn("Duplicate request detected for transaction ID: {}", request.getTransactionId());
            throw new RuntimeException("Transaction already processed");
        }

        Account from = accountRepository.findByAccountNumber(request.getFromAccount())
            .orElseThrow(() -> new RuntimeException("Source account not found"));

        // Check authorization - only owner account can access
        if (!from.getCitizenId().equals(authenticatedUser.getCitizenId())) {
            log.warn("Unauthorized access attempt by user: {}", authenticatedUser.getUsername());
            throw new RuntimeException("You are not the owner of this account");
        }

        var user = userRepository.findByEmail(authenticatedUser.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPin(), user.getPin())) {
            log.warn("Invalid PIN attempt for user: {}", authenticatedUser.getUsername());
            throw new RuntimeException("Invalid PIN");
        }

        Account to = accountRepository.findByAccountNumber(request.getToAccount())
            .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            log.warn("Insufficient balance for account: {}", from.getAccountNumber());
            throw new RuntimeException("Insufficient balance");
        }

        // Perform transfer
        BigDecimal newFromBalance = from.getBalance().subtract(request.getAmount());
        BigDecimal newToBalance = to.getBalance().add(request.getAmount());

        from.setBalance(newFromBalance);
        to.setBalance(newToBalance);

        accountRepository.save(from);
        accountRepository.save(to);

        // Save transaction (from perspective of sender)
        Transaction txn = Transaction.builder()
            .transactionId(request.getTransactionId())
            .fromAccount(from.getAccountNumber())
            .toAccount(to.getAccountNumber())
            .amount(request.getAmount())
            .resultingBalance(newFromBalance)
            .type("TRANSFER")
            .code("A1") // example
            .channel("ATS") // assume Online
            .remark("Transfer to x" + to.getAccountNumber().substring(to.getAccountNumber().length() - 4))
            .timestamp(LocalDateTime.now())
            .build();


        transactionRepository.save(txn);

        return new TransferResponse(
            txn.getId().toString(),
            from.getAccountNumber(),
            to.getAccountNumber(),
            request.getAmount(),
            from.getBalance()
        );
    }
}
