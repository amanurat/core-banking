
package com.banking.core.account.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateAccountRequest {
    private String citizenId;
    private String thaiName;
    private String englishName;

    private BigDecimal depositAmount = BigDecimal.ZERO;

    public void setDepositAmount(BigDecimal depositAmount) {
        if (depositAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Deposit amount must be a positive value");
        }
        this.depositAmount = depositAmount;
    }

}
