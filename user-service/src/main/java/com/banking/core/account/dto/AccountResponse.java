
package com.banking.core.account.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
    private String accountNumber;
    private BigDecimal balance;
}
