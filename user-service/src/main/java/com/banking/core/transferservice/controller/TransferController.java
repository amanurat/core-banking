package com.banking.core.transferservice.controller;

import com.banking.core.transferservice.dto.TransferRequest;
import com.banking.core.transferservice.dto.TransferResponse;
import com.banking.core.transferservice.service.TransferService;
import com.banking.core.userservice.dto.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<TransferResponse> transfer(
            @RequestBody TransferRequest request,
            @AuthenticationPrincipal UserDetail authenticatedUser) {

        TransferResponse response = transferService.transfer(request, authenticatedUser);
        return ResponseEntity.ok(response);
    }
}
