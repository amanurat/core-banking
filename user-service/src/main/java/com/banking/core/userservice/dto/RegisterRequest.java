package com.banking.core.userservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
  private String email;
  private String password;
  private String citizenId;
  private String thaiName;
  private String englishName;
  private String pin;

}
