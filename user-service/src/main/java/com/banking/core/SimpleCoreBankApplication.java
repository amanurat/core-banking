package com.banking.core;

import com.banking.core.account.entity.Account;
import com.banking.core.account.repository.AccountRepository;
import com.banking.core.userservice.entity.User;
import com.banking.core.userservice.repository.UserRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class SimpleCoreBankApplication {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;

  public static void main(String[] args) {
    SpringApplication.run(SimpleCoreBankApplication.class, args);
  }

  static final String SARA_CITYZEN = "1234567890124";

  @Bean
  public CommandLineRunner createTellerUsers() {
    return args -> {
      // Check if users already exist to avoid duplicates
      if (!userRepository.existsByEmail("teller001")) {
        User teller1 = User.builder()
            .email("john@bank.com")
            .citizenId("1234567890123")
            .role("TELLER")
            .password(passwordEncoder.encode("password"))
            .thaiName("จอน")
            .englishName("john")
            .pin(passwordEncoder.encode("123456"))
            .build();
        userRepository.save(teller1);
        log.info("Created Teller user: " + teller1.getEmail());
      }

      if (!userRepository.existsByEmail("teller002")) {
        User teller2 = User.builder()
            .email("sara@gmail.com")
            .citizenId(SARA_CITYZEN)
            .role("CUSTOMER")
            .password(passwordEncoder.encode("password"))
            .thaiName("ซาร่า")
            .englishName("sara")
            .pin(passwordEncoder.encode("123456"))
            .build();
        userRepository.save(teller2);
        log.info("Created Teller user: " + teller2.getEmail());
      }
      log.info("Customer user creation process completed.");

      // Create a new account for the customer
      // Create savings account for sara
      if (!accountRepository.existsByAccountNumber("1234567")) {
        Account saraAccount1 = Account.builder()
            .accountNumber("1234567")
            .citizenId(SARA_CITYZEN)
            .balance(BigDecimal.ZERO)
            .thaiName("ซาร่า")
            .englishName("sara")
            .balance(BigDecimal.valueOf(1000))
            .createdBy("john")
            .build();
        accountRepository.save(saraAccount1);
        log.info("Created savings account for sara: {}", saraAccount1.getAccountNumber());
      }

      // Create checking account for sara
      if (!accountRepository.existsByAccountNumber("1234568")) {
        Account saraAccount2 = Account.builder()
            .accountNumber("1234568")
            .citizenId(SARA_CITYZEN)
            .balance(BigDecimal.ZERO)
            .thaiName("ซาร่า")
            .balance(BigDecimal.valueOf(0))
            .englishName("sara")
            .createdBy("john")
            .build();
        accountRepository.save(saraAccount2);
        log.info("Created checking account for sara: {}", saraAccount2.getAccountNumber());
      }
    };
  }

}
