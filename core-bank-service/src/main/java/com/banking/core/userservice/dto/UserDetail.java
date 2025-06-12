package com.banking.core.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetail {
  private String username; // mapping email
    private String password;
    private String citizenId;
    private String thaiName;
    private String englishName;
    private String role; // CUSTOMER, TELLER
}