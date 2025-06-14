
package com.banking.core.account.controller;

import com.banking.core.account.dto.AccountResponse;
import com.banking.core.account.dto.CreateAccountRequest;
import com.banking.core.account.entity.Account;
import com.banking.core.account.service.AccountService;
import com.banking.core.userservice.dto.UserDetail;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasRole('TELLER')")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request, @AuthenticationPrincipal UserDetail authenticatedUser) {
        return ResponseEntity.ok(accountService.createAccount(request, authenticatedUser));
    }


    @PostMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<AccountResponse>> getMyAccounts(@AuthenticationPrincipal UserDetail authenticatedUser) {
        return ResponseEntity.ok(accountService.getMyAccounts(authenticatedUser));
    }

}
