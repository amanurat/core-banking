
package com.banking.core.transferservice.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private Long amount;
    private String pin;
}
