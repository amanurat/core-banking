
package com.banking.core.transferservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAccount;
    private String toAccount;

    private Long amount;

    private LocalDateTime timestamp;

    private String type; // e.g. "TRANSFER", "DEPOSIT"
}
