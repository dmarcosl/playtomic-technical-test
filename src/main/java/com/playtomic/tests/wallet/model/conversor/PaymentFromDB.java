package com.playtomic.tests.wallet.model.conversor;

import com.playtomic.tests.wallet.config.ConverterService;
import com.playtomic.tests.wallet.model.db.PaymentEntity;
import com.playtomic.tests.wallet.model.api.Payment;
import org.springframework.core.convert.converter.Converter;

@ConverterService
public class PaymentFromDB implements Converter<PaymentEntity, Payment> {

    @Override
    public Payment convert(PaymentEntity source) {
        Payment target = new Payment();
        target.setPaymentId(source.getPaymentId());
        target.setAmount(source.getAmount());
        target.setDate(source.getCreationDate());
        return target;
    }
}
