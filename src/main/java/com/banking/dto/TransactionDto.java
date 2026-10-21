package com.banking.dto;

import java.math.BigDecimal;

public class TransferDto {

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
