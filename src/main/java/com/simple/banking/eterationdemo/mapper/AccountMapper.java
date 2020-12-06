package com.simple.banking.eterationdemo.mapper;

import com.simple.banking.eterationdemo.entity.Account;
import com.simple.banking.eterationdemo.dto.AccountDto;
import com.simple.banking.eterationdemo.dto.TransactionDetails;
import com.simple.banking.eterationdemo.dto.AccountTransactionDto;

import java.util.Comparator;
import java.util.stream.Collectors;

public class AccountMapper {
    private AccountMapper() {
    }

    public static AccountDto account2AccountDto(Account account) {
        return AccountDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .surname(account.getSurname())
                .accountType(account.getAccountType())
                .build();
    }

    public static Account accountDto2Account(AccountDto accountDto) {
        return Account.builder()
                .name(accountDto.getName())
                .email(accountDto.getEmail())
                .surname(accountDto.getSurname())
                .accountType(accountDto.getAccountType())
                .build();
    }

    public static AccountTransactionDto getAccountTransactionDto(Account account) {
        return AccountTransactionDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .amount(account.getAmount())
                .surname(account.getSurname())
                .accountType(account.getAccountType())
                .transactionDetailsList(
                        account.getTransactionList().stream().map(transaction ->
                                TransactionDetails.builder()
                                        .id(transaction.getId())
                                        .newAmount(transaction.getNewAmount())
                                        .oldAmount(transaction.getOldAmount())
                                        .creationDate(transaction.getCreationDate())
                                        .transactionType(transaction.getTransactionType())
                                        .build()
                        )
                                .sorted(Comparator.comparing(TransactionDetails::getId)
                                        .reversed())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
