package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.api.exceptions.RestControllerError;
import com.playtomic.tests.wallet.api.exceptions.RestControllerException;
import com.playtomic.tests.wallet.model.api.Payment;
import com.playtomic.tests.wallet.model.api.Recharge;
import com.playtomic.tests.wallet.model.api.Wallet;
import com.playtomic.tests.wallet.service.PaymentService;
import com.playtomic.tests.wallet.service.exceptions.PaymentServiceException;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.exceptions.WalletServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("wallet")
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    private PaymentService paymentService;
    private WalletService walletService;

    @Autowired
    public WalletController(PaymentService paymentService, WalletService walletService) {
        this.paymentService = paymentService;
        this.walletService = walletService;
    }

    @PostMapping("")
    public Wallet createWallet() {
        return walletService.createWallet();
    }

    @GetMapping("/{idWallet}")
    public Wallet getWallet(@PathVariable("idWallet") Integer idWallet) throws RestControllerException {
        try {
            return walletService.getWallet(idWallet);
        } catch (WalletServiceException e) {
            throw new RestControllerException(new RestControllerError(e.getMessage()));
        }
    }

    @PutMapping("/{idWallet}/add-balance")
    public Recharge addBalance(@PathVariable("idWallet") Integer idWallet,
                               @RequestParam("amount") BigDecimal amount) throws RestControllerException {
        try {
            // Check that the wallet exists
            walletService.getWallet(idWallet);
            // Perform payment in the third party service
            paymentService.charge(amount);
            // Add the balance to the wallet
            return walletService.addBalance(idWallet, amount);
        } catch (PaymentServiceException e) {
            throw new RestControllerException(new RestControllerError(RestControllerException.MINIMUM_AMOUNT_TO_RECHARGE));
        } catch (WalletServiceException e) {
            throw new RestControllerException(new RestControllerError(e.getMessage()));
        }
    }

    @PutMapping("/{idWallet}/discount-balance")
    public Payment discountBalance(@PathVariable("idWallet") Integer idWallet,
                                   @RequestParam("amount") BigDecimal amount) throws RestControllerException {
        if (amount.compareTo(new BigDecimal(0)) < 1) {
            throw new RestControllerException(new RestControllerError(RestControllerException.INVALID_AMOUNT_TO_PAY));
        }

        try {
            // Check if the wallet has enough balance
            if (!walletService.checkBalance(idWallet, amount)) {
                throw new RestControllerException(new RestControllerError(RestControllerException.NOT_ENOUGH_BALANCE));
            }
            // Subtract the balance from the wallet
            return walletService.discountBalance(idWallet, amount);
        } catch (WalletServiceException e) {
            throw new RestControllerException(new RestControllerError(e.getMessage()));
        }
    }
}
