package com.playtomic.tests.wallet.model.conversor;

import com.playtomic.tests.wallet.config.ConverterService;
import com.playtomic.tests.wallet.model.db.PaymentEntity;
import com.playtomic.tests.wallet.model.db.RechargeEntity;
import com.playtomic.tests.wallet.model.db.WalletEntity;
import com.playtomic.tests.wallet.model.api.Payment;
import com.playtomic.tests.wallet.model.api.Recharge;
import com.playtomic.tests.wallet.model.api.Wallet;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.Comparator;
import java.util.stream.Collectors;

@ConverterService
public class WalletFromDB implements Converter<WalletEntity, Wallet> {

    private Converter<PaymentEntity, Payment> paymentConverter;
    private Converter<RechargeEntity, Recharge> rechargeConverter;

    @Autowired
    public WalletFromDB(Converter<PaymentEntity, Payment> paymentConverter,
                        Converter<RechargeEntity, Recharge> rechargeConverter) {
        this.paymentConverter = paymentConverter;
        this.rechargeConverter = rechargeConverter;
    }

    @Override
    public Wallet convert(WalletEntity source) {
        Wallet target = new Wallet();
        target.setWalletId(source.getWalletId());
        target.setBalance(source.getBalance());
        Hibernate.initialize(source);
        if (Hibernate.isInitialized(source.getPayments()) && !source.getPayments().isEmpty()) {
            target.setPayments(source.getPayments().stream().map(paymentConverter::convert).collect(Collectors.toList()));
            target.getPayments().sort(Comparator.comparingInt(Payment::getPaymentId));
        }
        if (Hibernate.isInitialized(source.getRecharges()) && !source.getRecharges().isEmpty()) {
            target.setRecharges(source.getRecharges().stream().map(rechargeConverter::convert).collect(Collectors.toList()));
            target.getRecharges().sort(Comparator.comparingInt(Recharge::getRechargeId));
        }
        return target;
    }
}
