package com.simple.banking.eterationdemo.exception;

import static com.simple.banking.eterationdemo.util.ResponseMessages.ACCOUNT_NOT_FOUND;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super(ACCOUNT_NOT_FOUND);
    }
}
