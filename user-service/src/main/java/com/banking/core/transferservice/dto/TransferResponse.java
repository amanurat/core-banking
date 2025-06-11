
package com.banking.core.transferservice.dto;

import java.math.BigDecimal;
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
    private BigDecimal amount;
    private BigDecimal remainingBalance;
}
