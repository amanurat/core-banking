
package com.banking.core.transferservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"transactionId"})
})
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

    private String transactionId;
}
