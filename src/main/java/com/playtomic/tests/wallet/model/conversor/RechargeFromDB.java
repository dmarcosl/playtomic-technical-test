package com.playtomic.tests.wallet.model.conversor;

import com.playtomic.tests.wallet.config.ConverterService;
import com.playtomic.tests.wallet.model.db.RechargeEntity;
import com.playtomic.tests.wallet.model.api.Recharge;
import org.springframework.core.convert.converter.Converter;

@ConverterService
public class RechargeFromDB implements Converter<RechargeEntity, Recharge> {

    @Override
    public Recharge convert(RechargeEntity source) {
        Recharge target = new Recharge();
        target.setRechargeId(source.getRechargeId());
        target.setAmount(source.getAmount());
        target.setDate(source.getCreationDate());
        return target;
    }
}
