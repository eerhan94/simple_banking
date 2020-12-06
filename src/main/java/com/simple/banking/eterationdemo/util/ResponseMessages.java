package com.simple.banking.eterationdemo.util;

public class ResponseMessages {
    private ResponseMessages() {
    }

    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String ACCOUNT_CREATED = "Account successfully created";
    public static final String ACCOUNT_UPDATED = "Account successfully updated";
    public static final String ACCOUNT_DELETED = "Account successfully deleted";

    public static final String ACCOUNT_ID_FIELD = "accountId";

    public static final String TRANSACTION_TYPE_NOT_FOUND = "Transaction type not found";
    public static final String TRANSACTION_COMPLETED = "Transaction successfully completed";

    public static final String TRANSACTION_ID_FIELD = "transactionId";

    public static final String INSUFFICIENT_BALANCE = "Insufficient balance";
}
