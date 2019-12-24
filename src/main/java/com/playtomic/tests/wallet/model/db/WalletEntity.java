package com.playtomic.tests.wallet.model.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "wallet")
@SequenceGenerator(name = "next_wallet_id", sequenceName = "wallet_seq", allocationSize = 1)
public class WalletEntity extends AuditionEntity {

    private Integer walletId;
    private BigDecimal balance;
    private Set<PaymentEntity> payments = new HashSet<>();
    private Set<RechargeEntity> recharges = new HashSet<>();

    @Id
    @Column(name = "wallet_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "next_wallet_id")
    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    @Column(name = "balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wallet")
    public Set<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentEntity> payments) {
        this.payments = payments;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wallet")
    public Set<RechargeEntity> getRecharges() {
        return recharges;
    }

    public void setRecharges(Set<RechargeEntity> recharges) {
        this.recharges = recharges;
    }
}
