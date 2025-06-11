package com.banking.core.transferservice.controller;

import com.banking.core.transferservice.dto.StatementItemResponse;
import com.banking.core.transferservice.dto.StatementRequest;
import com.banking.core.transferservice.entity.Transaction;
import com.banking.core.transferservice.repository.TransactionRepository;
import com.banking.core.transferservice.service.StatementService;
import com.banking.core.transferservice.service.TransferService;
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
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementController {

  private final StatementService statementService;
  private final TransactionRepository transactionRepository;
  private final TransferService transferService;

  @PostMapping
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<List<StatementItemResponse>> getStatement(
      @RequestBody StatementRequest request,
      @AuthenticationPrincipal UserDetail authenticated) {

    List<StatementItemResponse> result = statementService.getStatement(request, authenticated);
    return ResponseEntity.ok(result);
  }


  // TODO: Remove in production - development endpoint to show all transactions
  @GetMapping
  public ResponseEntity<List<Transaction>> findAllTransactions() {
    return ResponseEntity.ok(transactionRepository.findAll());
  }

}


