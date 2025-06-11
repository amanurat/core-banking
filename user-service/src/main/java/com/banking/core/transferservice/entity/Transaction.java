
package com.banking.core.transferservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String type; // e.g. "TRANSFER", "DEPOSIT"

    private String code;              // e.g. A0, A1
    private String channel;           // e.g. ATS, OTC
    private String remark;            // Transfer to x1234 Mr. X
    private BigDecimal resultingBalance;    // ยอดคงเหลือหลังทำรายการ (สำหรับบัญชีต้นทาง)
}
