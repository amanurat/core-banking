package com.banking.core.account.entity;

import com.banking.core.userservice.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"accountNumber"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 7)
    private String accountNumber; // 7 digit, unique

    @Column(nullable = false, length = 13)
    private String citizenId;

    @Column(nullable = false)
    private String thaiName;

    @Column(nullable = false)
    private String englishName;

    @Column(nullable = false)
    @Builder.Default  // Add this annotation
    private BigDecimal balance = BigDecimal.ZERO;


    private String createdBy; // teller user
}
