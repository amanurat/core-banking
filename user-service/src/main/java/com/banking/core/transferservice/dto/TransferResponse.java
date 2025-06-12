
package com.banking.core.transferservice.dto;

import com.banking.core.transferservice.entity.Transaction;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransferResponse implements Serializable {
    private String transactionId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private BigDecimal remainingBalance;


    public static TransferResponse fromEntity(Transaction transaction) {
        return TransferResponse.builder()
            .transactionId(transaction.getTransactionId())
            .fromAccount(transaction.getFromAccount())
            .toAccount(transaction.getToAccount())
            .amount(transaction.getAmount())
            .remainingBalance(transaction.getResultingBalance())
            .build();
    }
}


