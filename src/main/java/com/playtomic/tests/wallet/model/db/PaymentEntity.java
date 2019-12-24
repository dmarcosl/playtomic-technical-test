package com.playtomic.tests.wallet.model.db;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@SequenceGenerator(name = "next_payment_id", sequenceName = "payment_seq", allocationSize = 1)
public class PaymentEntity extends AuditionEntity {

    private Integer paymentId;
    private WalletEntity wallet;
    private BigDecimal amount;

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "next_payment_id")
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payments")
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
