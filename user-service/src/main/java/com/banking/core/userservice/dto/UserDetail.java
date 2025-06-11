package com.banking.core.userservice.dto;

import com.banking.core.userservice.entity.User;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

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