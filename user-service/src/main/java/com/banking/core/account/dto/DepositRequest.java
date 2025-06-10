package com.banking.core.account.dto;

import lombok.Data;

@Data
public class DepositRequest {
    private String accountNumber;
    private Long amount;
}
