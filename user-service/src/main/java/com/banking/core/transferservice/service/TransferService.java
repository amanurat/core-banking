
package com.banking.core.transferservice.service;

import com.banking.core.account.entity.Account;
import com.banking.core.account.repository.AccountRepository;
import com.banking.core.transferservice.dto.TransferRequest;
import com.banking.core.transferservice.dto.TransferResponse;
import com.banking.core.transferservice.entity.Transaction;
import com.banking.core.transferservice.repository.TransactionRepository;
import com.banking.core.userservice.dto.UserDetail;
import com.banking.core.userservice.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

    // Autowired Repository or Services e.g. AccountRepository, UserRepository, TransactionRepository, etc.

    /*public TransferResponse transfer(TransferRequest request, String userEmail) {
        // TODO:
        // 1. Verify fromAccount belongs to logged-in user (using userEmail or citizenId)
        // 2. Validate PIN
        // 3. Check toAccount exists
        // 4. Check sufficient balance
        // 5. Perform transfer and save transaction

        // This is a mock response. Implement actual logic with DB entities and validations.
        return new TransferResponse(
            "TXN123456",
            request.getFromAccount(),
            request.getToAccount(),
            request.getAmount(),
            1000L  // mock remaining balance
        );
    }*/


    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository; // assume shared or imported from user-service

    @Transactional
    public TransferResponse transfer(TransferRequest request, UserDetail authenticatedUser) {
        Account from = accountRepository.findByAccountNumber(request.getFromAccount())
            .orElseThrow(() -> new RuntimeException("Source account not found"));

        if (!from.getCitizenId().equals(authenticatedUser.getCitizenId())) {
            throw new RuntimeException("You are not the owner of this account");
        }

        var user = userRepository.findByEmail(authenticatedUser.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPin(), user.getPin())) {
            throw new RuntimeException("Invalid PIN");
        }

        Account to = accountRepository.findByAccountNumber(request.getToAccount())
            .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (from.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction txn = Transaction.builder()
            .fromAccount(from.getAccountNumber())
            .toAccount(to.getAccountNumber())
            .amount(request.getAmount())
            .timestamp(LocalDateTime.now())
            .type("TRANSFER")
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
