package com.banking.core.userservice.service;

import com.banking.core.userservice.dto.RegisterRequest;
import com.banking.core.userservice.dto.UserResponse;
import com.banking.core.userservice.entity.User;
import com.banking.core.userservice.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

  private final UserRepository userRepository;

  public UserResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email already exists");
    }
    if (userRepository.existsByCitizenId(request.getCitizenId())) {
      throw new RuntimeException("Citizen ID already exists");
    }

    User user = User.builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .citizenId(request.getCitizenId())
        .thaiName(request.getThaiName())
        .englishName(request.getEnglishName())
        .pinHash(request.getPin())
        .role("CUSTOMER")
        .build();

    user = userRepository.save(user);

    return new UserResponse(user.getId(), user.getEmail(), user.getRole());

  }
}