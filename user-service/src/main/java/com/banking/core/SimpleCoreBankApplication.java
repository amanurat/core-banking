package com.banking.core;

import com.banking.core.userservice.entity.User;
import com.banking.core.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SimpleCoreBankApplication {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(SimpleCoreBankApplication.class, args);
  }

  @Bean
  public CommandLineRunner createTellerUsers() {
    return args -> {
      // Check if users already exist to avoid duplicates
      if (!userRepository.existsByEmail("teller001")) {
        User teller1 = new User();
        teller1.setEmail("john@bank.com");
        teller1.setCitizenId("1234567890123");
        teller1.setRole("TELLER");
        teller1.setPassword(passwordEncoder.encode("password"));
        teller1.setThaiName("จอน");
        teller1.setEnglishName("john");
        teller1.setPin(passwordEncoder.encode("123456"));
        userRepository.save(teller1);
        System.out.println("Created Teller user: " + teller1.getEmail());
      }

      if (!userRepository.existsByEmail("teller002")) {
        User teller2 = new User();
        teller2.setEmail("sara@gmail.com");
        teller2.setCitizenId("1234567890124");
        teller2.setRole("CUSTOMER");
        teller2.setPassword(passwordEncoder.encode("password"));
        teller2.setThaiName("ซาร่า");
        teller2.setEnglishName("sara");
        teller2.setPin(passwordEncoder.encode("123456"));
        userRepository.save(teller2);
        System.out.println("Created Teller user: " + teller2.getEmail());
      }

      System.out.println("Customer user creation process completed.");
    };
  }

}
