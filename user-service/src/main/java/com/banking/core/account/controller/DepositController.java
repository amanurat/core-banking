package com.banking.core.account.controller;

import com.banking.core.account.dto.DepositRequest;
import com.banking.core.account.dto.DepositResponse;
import com.banking.core.account.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deposits")
@RequiredArgsConstructor
public class DepositController {

  private final DepositService depositService;

  @PostMapping
  public ResponseEntity<DepositResponse> deposit(@RequestBody DepositRequest request) {
    // จำลอง role จาก security context (จริงควรดึงจาก JWT auth)
    String role = "TELLER";

    return ResponseEntity.ok(depositService.deposit(request, role));
  }
}
