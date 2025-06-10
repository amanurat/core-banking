
package com.banking.core.account.dto;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String citizenId;
    private String thaiName;
    private String englishName;
}
