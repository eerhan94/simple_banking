package com.simple.banking.eterationdemo.service;

import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.dto.TransactionDto;
import com.simple.banking.eterationdemo.dto.AccountTransactionDto;
import com.simple.banking.eterationdemo.exception.TransactionException;

import java.util.List;

public interface TransactionService {
    CommonDto create(TransactionDto transactionDto) throws TransactionException;

    AccountTransactionDto getTransactionsById(Long id) throws TransactionException;

    List<AccountTransactionDto> getAllTransactions();
}
