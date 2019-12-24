package com.playtomic.tests.wallet.model.api;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {

    private Integer paymentId;
    private BigDecimal amount;
    private Date date;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
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
