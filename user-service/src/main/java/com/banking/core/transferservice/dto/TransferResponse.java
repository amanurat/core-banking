
package com.banking.core.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransferResponse {
    private String transactionId;
    private String fromAccount;
    private String toAccount;
    private Long amount;
    private Long remainingBalance;
}
