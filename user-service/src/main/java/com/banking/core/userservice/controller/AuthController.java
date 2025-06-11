package com.banking.core.userservice.controller;

import com.banking.core.userservice.dto.LoginRequest;
import com.banking.core.userservice.dto.LoginResponse;
import com.banking.core.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("******* Login.... *******");
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
