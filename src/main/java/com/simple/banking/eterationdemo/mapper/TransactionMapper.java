package com.simple.banking.eterationdemo.mapper;

import com.simple.banking.eterationdemo.entity.Transaction;
import com.simple.banking.eterationdemo.dto.TransactionDto;

public class TransactionMapper {
    private TransactionMapper() {
    }

    public static Transaction transactionDto2Transaction(TransactionDto transactionDto) {
        return Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .build();
    }
}
