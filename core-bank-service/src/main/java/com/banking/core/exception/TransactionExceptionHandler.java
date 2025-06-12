package com.banking.core.exception;

import com.banking.core.account.dto.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {
    
    @ExceptionHandler(ConcurrentTransactionException.class)
    public ResponseEntity<ErrorResponse> handleConcurrentTransaction(ConcurrentTransactionException e) {
        ErrorResponse error = ErrorResponse.builder()
//            .code("CONCURRENT_TRANSACTION")
            .message("Transaction is already being processed")
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTransaction(DataIntegrityViolationException e) {
        ErrorResponse error = ErrorResponse.builder()
//            .code("DUPLICATE_TRANSACTION")
            .message("Transaction already exists")
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}