package com.banking.core.userservice.service;

import com.banking.core.userservice.dto.RegisterRequest;
import com.banking.core.userservice.dto.UserResponse;
import com.banking.core.userservice.entity.User;
import com.banking.core.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  public UserResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email already exists");
    }
    if (userRepository.existsByCitizenId(request.getCitizenId())) {
      throw new RuntimeException("Citizen ID already exists");
    }

    User user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .citizenId(request.getCitizenId())
        .thaiName(request.getThaiName())
        .englishName(request.getEnglishName())
        .pin(passwordEncoder.encode(request.getPin()))
        .role("CUSTOMER")
        .build();

    user = userRepository.save(user);

    return new UserResponse(user.getId(), user.getEmail(), user.getRole());

  }
}