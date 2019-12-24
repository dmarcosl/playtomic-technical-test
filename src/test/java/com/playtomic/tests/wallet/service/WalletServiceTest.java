package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.api.Payment;
import com.playtomic.tests.wallet.model.api.Recharge;
import com.playtomic.tests.wallet.model.api.Wallet;
import com.playtomic.tests.wallet.repository.PaymentRepository;
import com.playtomic.tests.wallet.repository.RechargeRepository;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.exceptions.WalletServiceException;
import org.junit.After;
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
public class WalletServiceTest {

    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    RechargeRepository rechargeRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @After
    public void tearDown() throws Exception {
        paymentRepository.deleteAll();
        rechargeRepository.deleteAll();
        walletRepository.deleteAll();
    }

    /**
     * Create a wallet
     */
    @Test
    public void createWallet_ok() {
        Wallet wallet = walletService.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());
    }

    /**
     * Create and retrieve a wallet
     */
    @Test
    public void getWallet_ok() throws WalletServiceException {
        // Create a new wallet
        Wallet wallet = walletService.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Retrieve it and check that both are the same
        Wallet wallet2 = walletService.getWallet(wallet.getWalletId());
        Assert.assertNotNull(wallet2);
        Assert.assertEquals((int) wallet.getWalletId(), (int) wallet2.getWalletId());
    }

    /**
     * Retrieve a wallet that doesn't exist
     */
    @Test(expected = WalletServiceException.class)
    public void getWallet_notFound() throws WalletServiceException {
        walletService.getWallet(15);
    }

    /**
     * Create a wallet and add balance
     */
    @Test
    public void addBalance_ok() throws WalletServiceException {
        // Create a new wallet
        Wallet wallet = walletService.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Add some balance
        Recharge recharge = walletService.addBalance(wallet.getWalletId(), new BigDecimal(15));
        Assert.assertNotNull(recharge);
        Assert.assertNotNull(recharge.getRechargeId());
        Assert.assertEquals(0, recharge.getAmount().compareTo(new BigDecimal(15)));

        // Retrieve the wallet to get the updates and check them
        wallet = walletService.getWallet(wallet.getWalletId());
        Assert.assertNotNull(wallet);
        Assert.assertEquals(0, wallet.getBalance().compareTo(new BigDecimal(15)));
        Assert.assertEquals(1, wallet.getRecharges().size());
    }

    /**
     * Check the balance
     */
    @Test
    public void checkBalance_ok() throws WalletServiceException {
        // Create a new wallet
        Wallet wallet = walletService.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Check that the balance is 0
        Assert.assertTrue(walletService.checkBalance(wallet.getWalletId(), new BigDecimal(0)));

        // Add some balance
        Recharge recharge = walletService.addBalance(wallet.getWalletId(), new BigDecimal(50));
        Assert.assertNotNull(recharge);
        Assert.assertNotNull(recharge.getRechargeId());
        Assert.assertEquals(0, recharge.getAmount().compareTo(new BigDecimal(50)));

        // Check that the balance has been updated
        Assert.assertTrue(walletService.checkBalance(wallet.getWalletId(), new BigDecimal(50)));
    }

    /**
     * Add balance and discount it
     */
    @Test
    public void discountBalance_ok() throws WalletServiceException {
        // Create a new wallet
        Wallet wallet = walletService.createWallet();
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());

        // Add some balance
        Recharge recharge = walletService.addBalance(wallet.getWalletId(), new BigDecimal(15));
        Assert.assertNotNull(recharge);
        Assert.assertNotNull(recharge.getRechargeId());
        Assert.assertEquals(0, recharge.getAmount().compareTo(new BigDecimal(15)));

        // Discount some balance
        Payment payment = walletService.discountBalance(wallet.getWalletId(), new BigDecimal(5));
        Assert.assertNotNull(payment);
        Assert.assertNotNull(payment.getPaymentId());
        Assert.assertEquals(0, payment.getAmount().compareTo(new BigDecimal(5)));

        // Retrieve the wallet to get the updates and check them
        wallet = walletService.getWallet(wallet.getWalletId());
        Assert.assertNotNull(wallet);
        Assert.assertNotNull(wallet.getWalletId());
        Assert.assertEquals(0, wallet.getBalance().compareTo(new BigDecimal(10)));
        Assert.assertEquals(1, wallet.getRecharges().size());
        Assert.assertEquals(1, wallet.getPayments().size());
    }
}
