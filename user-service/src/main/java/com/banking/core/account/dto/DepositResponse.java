package com.banking.core.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositResponse {
    private String accountNumber;
    private Long newBalance;
}
