package com.banking.core.transferservice.service;

import com.banking.core.account.entity.Account;
import com.banking.core.account.repository.AccountRepository;
import com.banking.core.transferservice.dto.StatementItemResponse;
import com.banking.core.transferservice.dto.StatementRequest;
import com.banking.core.transferservice.entity.Transaction;
import com.banking.core.transferservice.repository.TransactionRepository;
import com.banking.core.userservice.dto.UserDetail;
import com.banking.core.userservice.entity.User;
import com.banking.core.userservice.repository.UserRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatementService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public List<StatementItemResponse> getStatement(StatementRequest request, UserDetail authenticated) {
    // Check authorization - only owner account can access
    Account acc = accountRepository.findByAccountNumber(request.getAccountNumber())
        .orElseThrow(() -> new RuntimeException("Account not found"));

    if (!acc.getCitizenId().equals(authenticated.getCitizenId())) {
      throw new RuntimeException("Access denied: not your account");
    }

    User user = userRepository.findByEmail(authenticated.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(request.getPin(), user.getPin())) {
      throw new RuntimeException("Invalid PIN");
    }

    List<Transaction> transactions = transactionRepository.findByAccountAndMonth(
        acc.getAccountNumber(), request.getYear(), request.getMonth());

    return transactions.stream().map(tx -> {
      boolean isDebit = tx.getFromAccount() != null &&
                        tx.getFromAccount().equals(acc.getAccountNumber());

      return StatementItemResponse.builder()
          .date(tx.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
          .time(tx.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
          .code(tx.getCode())
          .channel(tx.getChannel())
          .debitCredit(isDebit ? tx.getAmount().negate() : tx.getAmount())
          .balance(tx.getResultingBalance())
          .remark(tx.getRemark())
          .build();
    }).toList();  }
}
