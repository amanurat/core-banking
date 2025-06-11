package com.banking.core.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;

  @NotBlank(message = "Citizen ID is required")
  @Pattern(regexp = "^[0-9]{13}$", message = "Citizen ID must be 13 digits")
  private String citizenId;

  @NotBlank(message = "Thai name is required")
  private String thaiName;

  @NotBlank(message = "English name is required")
  private String englishName;

  @NotBlank(message = "PIN is required")
  @Pattern(regexp = "^[0-9]{6}$", message = "PIN must be exactly 6 digits")
  private String pin;
}