
package com.banking.core.transferservice.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransferRequest {
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotNull(message = "From account is required")
    private String fromAccount;
    @NotNull(message = "To account is required")
    private String toAccount;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Pin is required")
    private String pin;

    // Validation
    @AssertTrue(message = "From and To accounts must be different")
    public boolean isValidAccounts() {
        return fromAccount != null && toAccount != null &&
               !fromAccount.equals(toAccount);
    }
}
