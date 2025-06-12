package com.banking.core.transferservice.dto;

import lombok.Data;

@Data
public class StatementRequest {
    private String accountNumber;
    private int year;
    private int month;
    private String pin;
}
