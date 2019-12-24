package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.api.exceptions.RestControllerException;
import com.playtomic.tests.wallet.model.api.Payment;
import com.playtomic.tests.wallet.model.api.Recharge;
import com.playtomic.tests.wallet.model.api.Wallet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletControllerTest {

    @Autowired
    private WalletController walletController;

    /**
     * Create a wallet
     */
    @Test
    public void createWallet_ok() {
        Wallet wallet = walletController.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());
    }

    /**
     * Create and retrieve a wallet
     */
    @Test
    public void getWallet_ok() throws RestControllerException {
        // Create a new wallet
        Wallet wallet = walletController.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Retrive it and check that both are the same
        Wallet wallet2 = walletController.getWallet(wallet.getWalletId());
        Assert.assertNotNull(wallet2);
        Assert.assertEquals((int) wallet.getWalletId(), (int) wallet2.getWalletId());
    }

    /**
     * Retrieve a wallet that doesn't exist
     */
    @Test(expected = RestControllerException.class)
    public void getWallet_notFound() throws RestControllerException {
        walletController.getWallet(15);
    }

    /**
     * Create a wallet and add balance
     */
    @Test
    public void addBalance_ok() throws RestControllerException {
        // Create a new wallet
        Wallet wallet = walletController.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Add some balance
        Recharge recharge = walletController.addBalance(wallet.getWalletId(), new BigDecimal(15));
        Assert.assertNotNull(recharge);
        Assert.assertNotNull(recharge.getRechargeId());
        Assert.assertEquals(0, recharge.getAmount().compareTo(new BigDecimal(15)));

        // Retrieve the wallet to get the updates and check them
        wallet = walletController.getWallet(wallet.getWalletId());
        Assert.assertNotNull(wallet);
        Assert.assertEquals(0, wallet.getBalance().compareTo(new BigDecimal(15)));
        Assert.assertEquals(1, wallet.getRecharges().size());
    }

    /**
     * Add balance to a wallet that doesn't exist
     */
    @Test(expected = RestControllerException.class)
    public void addBalance_walletNotFound() throws RestControllerException {
        walletController.addBalance(505, new BigDecimal(15));
    }

    /**
     * Add less balance that the allowed
     */
    @Test(expected = RestControllerException.class)
    public void addBalance_insufficientAmount() throws RestControllerException {
        // Create a new wallet
        Wallet wallet = walletController.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        walletController.addBalance(wallet.getWalletId(), new BigDecimal(1));
    }

    /**
     * Create a wallet, add balance and discount it
     */
    @Test
    public void discountBalance_ok() throws RestControllerException {
        // Create a new wallet
        Wallet wallet = walletController.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Add some balance
        Recharge recharge = walletController.addBalance(wallet.getWalletId(), new BigDecimal(15));
        Assert.assertNotNull(recharge);
        Assert.assertNotNull(recharge.getRechargeId());
        Assert.assertEquals(0, recharge.getAmount().compareTo(new BigDecimal(15)));

        // Discount some balance
        Payment payment = walletController.discountBalance(wallet.getWalletId(), new BigDecimal(5));
        Assert.assertNotNull(payment);
        Assert.assertNotNull(payment.getPaymentId());
        Assert.assertEquals(0, payment.getAmount().compareTo(new BigDecimal(5)));

        // Retrieve the wallet to get the updates and check them
        wallet = walletController.getWallet(wallet.getWalletId());
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());
        Assert.assertEquals(0, wallet.getBalance().compareTo(new BigDecimal(10)));
        Assert.assertEquals(1, wallet.getRecharges().size());
        Assert.assertEquals(1, wallet.getPayments().size());
    }

    /**
     * Discount less than 0
     */
    @Test(expected = RestControllerException.class)
    public void discountBalance_invalidAmount() throws RestControllerException {
        walletController.discountBalance(1, new BigDecimal(-25));
    }

    /**
     * Discount balance to a wallet that doesn't exist
     */
    @Test(expected = RestControllerException.class)
    public void discountBalance_walletNotFound() throws RestControllerException {
        walletController.discountBalance(247, new BigDecimal(5));
    }

    /**
     * Discount more balance that the wallet has
     */
    @Test(expected = RestControllerException.class)
    public void discountBalance_notEnoughBalance() throws RestControllerException {
        // Create a new wallet
        Wallet wallet = walletController.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Discount some balance
        walletController.discountBalance(wallet.getWalletId(), new BigDecimal(5));
    }
}
