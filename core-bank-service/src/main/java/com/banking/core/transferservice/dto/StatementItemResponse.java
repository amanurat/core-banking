package com.banking.core.transferservice.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StatementItemResponse {
    private String date;
    private String time;
    private String code;
    private String channel;
    private BigDecimal debitCredit;
    private BigDecimal balance;
    private String remark;
}
