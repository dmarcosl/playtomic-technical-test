package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.api.Payment;
import com.playtomic.tests.wallet.model.api.Recharge;
import com.playtomic.tests.wallet.model.api.Wallet;
import com.playtomic.tests.wallet.service.exceptions.WalletServiceException;

import java.math.BigDecimal;

public interface WalletService {

    /**
     * Create a new wallet
     *
     * @return New wallet
     */
    Wallet createWallet();

    /**
     * Retrieves a wallet by its id
     *
     * @param idWallet Id of the wallet
     * @return Wallet
     * @throws WalletServiceException Wallet not found
     */
    Wallet getWallet(Integer idWallet) throws WalletServiceException;

    /**
     * Add balance to a wallet creating a recharge
     *
     * @param idWallet Id of the wallet
     * @param amount   Amount to add
     * @return Recharge done
     */
    Recharge addBalance(Integer idWallet, BigDecimal amount);

    /**
     * Check if the wallet has enough balance
     *
     * @param idWallet Id of the wallet
     * @param amount   Amount to check
     * @return If the wallet has enough balance
     * @throws WalletServiceException Wallet not found
     */
    boolean checkBalance(Integer idWallet, BigDecimal amount) throws WalletServiceException;

    /**
     * Discount balance from a wallet creating a payment
     *
     * @param idWallet Id of the wallet
     * @param amount   Amount to subtract
     * @return Payment done
     */
    Payment discountBalance(Integer idWallet, BigDecimal amount);
}
