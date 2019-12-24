package com.playtomic.tests.wallet.service.exceptions;

public class WalletServiceException extends Exception {

    public static String WALLET_NOT_FOUND = "Wallet not found";

    public WalletServiceException(String message) {
        super(message);
    }
}
