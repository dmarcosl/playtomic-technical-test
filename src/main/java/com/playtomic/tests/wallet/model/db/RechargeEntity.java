package com.playtomic.tests.wallet.model.db;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "recharge")
@SequenceGenerator(name = "next_recharge_id", sequenceName = "recharge_seq", allocationSize = 1)
public class RechargeEntity extends AuditionEntity {

    private Integer rechargeId;
    private WalletEntity wallet;
    private BigDecimal amount;

    @Id
    @Column(name = "recharge_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "next_recharge_id")
    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recharges")
    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
