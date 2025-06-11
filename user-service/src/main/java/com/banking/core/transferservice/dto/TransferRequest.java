
package com.banking.core.transferservice.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String pin;
}
