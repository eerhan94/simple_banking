package com.simple.banking.eterationdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.entity.Account;
import com.simple.banking.eterationdemo.entity.Transaction;
import com.simple.banking.eterationdemo.dto.TransactionDto;
import com.simple.banking.eterationdemo.mapper.AccountMapper;
import org.springframework.transaction.annotation.Transactional;
import com.simple.banking.eterationdemo.mapper.TransactionMapper;
import com.simple.banking.eterationdemo.dto.AccountTransactionDto;
import com.simple.banking.eterationdemo.service.TransactionService;
import com.simple.banking.eterationdemo.repository.AccountRepository;
import com.simple.banking.eterationdemo.exception.TransactionException;
import com.simple.banking.eterationdemo.repository.TransactionRepository;

import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.simple.banking.eterationdemo.util.ResponseMessages.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public CommonDto create(TransactionDto transactionDto) throws TransactionException {
        Optional<Account> optionalAccount = accountRepository.findById(transactionDto.getAccountId());
        if (!optionalAccount.isPresent())
            throw new TransactionException();
        Account account = optionalAccount.get();
        Transaction transaction = TransactionMapper.transactionDto2Transaction(transactionDto);
        transactionCheckAndApply(transactionDto, transaction, account);
        account.getTransactionList().add(transaction);
        transaction.setAccount(account);
        transaction = transactionRepository.save(transaction);
        accountRepository.save(account);
        HashMap<String, String> message = new HashMap<>();
        message.put(TRANSACTION_ID_FIELD, String.valueOf(transaction.getId()));
        message.put(Transaction.class.getSimpleName(), TRANSACTION_COMPLETED);
        return CommonDto.builder().message(message).build();
    }

    @Override
    public AccountTransactionDto getTransactionsById(Long id) throws TransactionException {
        Optional<Account> account = accountRepository.findById(id);
        if (!account.isPresent())
            throw new TransactionException();
        return AccountMapper.getAccountTransactionDto(account.get());
    }

    @Override
    public List<AccountTransactionDto> getAllTransactions() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream().map(AccountMapper::getAccountTransactionDto).collect(Collectors.toList());
    }



    public void transactionCheckAndApply(TransactionDto transactionDto, Transaction transaction, Account account) throws TransactionException {
        switch (transactionDto.getTransactionType()) {
            case WITHDRAWAL:
            case BILL_PAYMENT:
                if (transactionDto.getAmount() > account.getAmount())
                    throw new TransactionException(INSUFFICIENT_BALANCE);
                transaction.setOldAmount(account.getAmount());
                account.setAmount(account.getAmount() - transactionDto.getAmount());
                transaction.setNewAmount(account.getAmount());
                break;
            case DEPOSIT:
                transaction.setOldAmount(account.getAmount());
                account.setAmount(account.getAmount() + transactionDto.getAmount());
                transaction.setNewAmount(account.getAmount());
                break;
            default:
                throw new TransactionException(TRANSACTION_TYPE_NOT_FOUND);
        }
    }
}
