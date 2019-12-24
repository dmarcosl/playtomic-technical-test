package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.model.api.Payment;
import com.playtomic.tests.wallet.model.api.Recharge;
import com.playtomic.tests.wallet.model.api.Wallet;
import com.playtomic.tests.wallet.model.db.PaymentEntity;
import com.playtomic.tests.wallet.model.db.RechargeEntity;
import com.playtomic.tests.wallet.model.db.WalletEntity;
import com.playtomic.tests.wallet.repository.PaymentRepository;
import com.playtomic.tests.wallet.repository.RechargeRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.exceptions.WalletServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;
    private PaymentRepository paymentRepository;
    private RechargeRepository rechargeRepository;
    private ConversionService conversionService;

    public WalletServiceImpl(WalletRepository walletRepository,
                             PaymentRepository paymentRepository,
                             RechargeRepository rechargeRepository,
                             @Qualifier("converterService") ConversionService conversionService) {
        this.walletRepository = walletRepository;
        this.paymentRepository = paymentRepository;
        this.rechargeRepository = rechargeRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Wallet createWallet() {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setBalance(new BigDecimal(0));
        walletRepository.save(walletEntity);
        return conversionService.convert(walletEntity, Wallet.class);
    }

    @Override
    public Wallet getWallet(Integer idWallet) throws WalletServiceException {
        return Optional.ofNullable(walletRepository.findOne(idWallet)).map(walletEntity -> conversionService.convert(walletEntity, Wallet.class))
                .orElseThrow(() -> new WalletServiceException(WalletServiceException.WALLET_NOT_FOUND));
    }

    @Override
    public Recharge addBalance(Integer idWallet, BigDecimal amount) {
        // Add the amount to the wallet
        WalletEntity walletEntity = walletRepository.findOne(idWallet);
        walletEntity.setBalance(walletEntity.getBalance().add(amount));
        walletRepository.save(walletEntity);

        // Create a recharge to store the transaction data
        RechargeEntity rechargeEntity = new RechargeEntity();
        rechargeEntity.setWallet(walletEntity);
        rechargeEntity.setAmount(amount);
        rechargeRepository.save(rechargeEntity);

        return conversionService.convert(rechargeEntity, Recharge.class);
    }

    @Override
    public boolean checkBalance(Integer idWallet, BigDecimal amount) throws WalletServiceException {
        return getWallet(idWallet).getBalance().subtract(amount).compareTo(new BigDecimal(0)) >= 0;
    }

    @Override
    public Payment discountBalance(Integer idWallet, BigDecimal amount) {
        // Subtract the amount from the wallet
        WalletEntity walletEntity = walletRepository.findOne(idWallet);
        walletEntity.setBalance(walletEntity.getBalance().subtract(amount));
        walletRepository.save(walletEntity);

        // Create a payment to store the transaction data
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setWallet(walletEntity);
        paymentEntity.setAmount(amount);
        paymentRepository.save(paymentEntity);

        return conversionService.convert(paymentEntity, Payment.class);
    }
}
