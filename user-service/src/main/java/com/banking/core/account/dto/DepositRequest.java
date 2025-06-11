package com.banking.core.account.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DepositRequest {
    private String accountNumber;
    private BigDecimal amount;
}
