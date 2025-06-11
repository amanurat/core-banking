package com.banking.core.account.controller;

import com.banking.core.account.dto.DepositRequest;
import com.banking.core.account.dto.DepositResponse;
import com.banking.core.account.service.DepositService;
import com.banking.core.userservice.dto.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
  @PreAuthorize("hasRole('TELLER')")
  public ResponseEntity<DepositResponse> deposit(@RequestBody DepositRequest request, @AuthenticationPrincipal UserDetail userDetail) {
    // จำลอง role จาก security context (จริงควรดึงจาก JWT auth)
//    String role = "TELLER";

    System.out.println(userDetail);
    return ResponseEntity.ok(depositService.deposit(request, userDetail.getRole()));
  }
}
