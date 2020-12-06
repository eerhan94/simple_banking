package com.simple.banking.eterationdemo.exception;

import static com.simple.banking.eterationdemo.util.ResponseMessages.ACCOUNT_NOT_FOUND;

public class TransactionException extends Exception {
    public TransactionException() {
        super(ACCOUNT_NOT_FOUND);
    }

    public TransactionException(String message) {
        super(message);
    }
}
