package com.banking.core.account.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositResponse {
    private String accountNumber;
    private BigDecimal newBalance;
}
