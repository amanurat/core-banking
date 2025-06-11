package com.banking.core.account.service;

import com.banking.core.account.dto.DepositRequest;
import com.banking.core.account.dto.DepositResponse;
import com.banking.core.account.entity.Account;
import com.banking.core.account.repository.AccountRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final AccountRepository accountRepository;

    public DepositResponse deposit(DepositRequest request, String tellerRole) {
        if (!"TELLER".equals(tellerRole)) {
            throw new RuntimeException("Only TELLER can deposit");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ONE) < 0) {
            throw new RuntimeException("Deposit amount must be at least 1 THB");
        }

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal newBalance = (account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO).add(request.getAmount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        return new DepositResponse(account.getAccountNumber(), newBalance);
    }}
