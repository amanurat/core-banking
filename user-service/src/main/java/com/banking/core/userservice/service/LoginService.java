package com.banking.core.userservice.service;

import com.banking.core.userservice.dto.LoginRequest;
import com.banking.core.userservice.dto.LoginResponse;
import com.banking.core.userservice.entity.User;
import com.banking.core.userservice.repository.UserRepository;
import com.banking.core.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = jwtUtil.generateToken(user);
    return new LoginResponse(token, user.getEmail(), user.getRole());
  }
}
