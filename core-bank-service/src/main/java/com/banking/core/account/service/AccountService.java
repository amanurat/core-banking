
package com.banking.core.account.service;

import com.banking.core.account.dto.AccountResponse;
import com.banking.core.account.dto.CreateAccountRequest;
import com.banking.core.account.entity.Account;
import com.banking.core.account.repository.AccountRepository;
import com.banking.core.userservice.dto.UserDetail;
import java.security.SecureRandom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;

  private final SecureRandom random = new SecureRandom();

  @Transactional
  public AccountResponse createAccount(CreateAccountRequest request, UserDetail authenticatedUser) {
    String accountNumber = generateUniqueAccountNumber();

    Account account = Account.builder()
        .accountNumber(accountNumber)
        .citizenId(request.getCitizenId())
        .thaiName(request.getThaiName())
        .englishName(request.getEnglishName())
        .createdBy(authenticatedUser.getThaiName())
        .balance(request.getDepositAmount())
        .build();

    account = accountRepository.save(account);

    return new AccountResponse(account.getAccountNumber(), account.getBalance());
  }

  protected String generateUniqueAccountNumber() {
    String accountNumber;
    int maxAttempts = 10;
    int attempts = 0;

    do {
      // Generate a random 7-digit account number with leading zeros
      accountNumber = String.format("%07d", random.nextInt(10_000_000));

      attempts++;
      // Prevent infinite loop by throwing exception after max attempts
      if (attempts >= maxAttempts) {
        throw new RuntimeException("Failed to generate unique account number after " + maxAttempts + " attempts");
      }
    } while (accountRepository.existsByAccountNumber(accountNumber));

    return accountNumber;
  }

  public List<Account> getAllAccounts() {
    return accountRepository.findAll();

  }
}
