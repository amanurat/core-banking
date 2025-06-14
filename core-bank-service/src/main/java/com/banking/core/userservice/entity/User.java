package com.banking.core.userservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"citizenId"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email; // using email as username

  private String password; // hashed

  @Column(length = 13, nullable = false)
  private String citizenId;

  @Column(nullable = false)
  private String thaiName;

  @Column(nullable = false)
  private String englishName;

  private String pin; // hashed 6-digit PIN

  private String role; // CUSTOMER, TELLER

}
