package com.playtomic.tests.wallet.model.api;

import java.math.BigDecimal;
import java.util.List;

public class Wallet {

    private Integer walletId;
    private BigDecimal balance;
    private List<Recharge> recharges;
    private List<Payment> payments;

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Recharge> getRecharges() {
        return recharges;
    }

    public void setRecharges(List<Recharge> recharges) {
        this.recharges = recharges;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
