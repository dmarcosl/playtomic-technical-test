package com.playtomic.tests.wallet.model.api;

import java.math.BigDecimal;
import java.util.Date;

public class Recharge {

    private Integer rechargeId;
    private BigDecimal amount;
    private Date date;

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
