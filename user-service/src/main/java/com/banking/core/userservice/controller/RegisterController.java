package com.banking.core.userservice.controller;


import com.banking.core.userservice.dto.RegisterRequest;
import com.banking.core.userservice.dto.UserResponse;
import com.banking.core.userservice.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RegisterController {

  private final RegisterService registerService;

  @PostMapping("/register")
  public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(registerService.register(request));
  }
}
