package com.simple.banking.eterationdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.dto.AccountDto;
import com.simple.banking.eterationdemo.entity.Account;
import com.simple.banking.eterationdemo.mapper.AccountMapper;
import com.simple.banking.eterationdemo.util.ResponseMessages;
import com.simple.banking.eterationdemo.service.AccountService;
import org.springframework.transaction.annotation.Transactional;
import com.simple.banking.eterationdemo.repository.AccountRepository;
import com.simple.banking.eterationdemo.exception.AccountNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

import static com.simple.banking.eterationdemo.util.ResponseMessages.ACCOUNT_ID_FIELD;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public CommonDto create(AccountDto accountDto) {
        Account account = AccountMapper.accountDto2Account(accountDto);
        account.setAmount(0.0);
        account = accountRepository.save(account);
        HashMap<String, String> message = new HashMap<>();
        message.put(ACCOUNT_ID_FIELD, String.valueOf(account.getId()));
        return CommonDto.builder().message(createCommonDtoResponseMap(Account.class.getSimpleName(), ResponseMessages.ACCOUNT_CREATED, message)).build();
    }

    @Override
    public AccountDto read(Long id) throws AccountNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if (!account.isPresent())
            throw new AccountNotFoundException();
        return AccountMapper.account2AccountDto(account.get());
    }

    @Override
    public CommonDto update(Long id, AccountDto accountDto) throws AccountNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent())
            throw new AccountNotFoundException();
        Account account = AccountMapper.accountDto2Account(accountDto);
        account.setId(optionalAccount.get().getId());
        accountRepository.save(account);
        return CommonDto.builder().message(createCommonDtoResponseMap(Account.class.getSimpleName(), ResponseMessages.ACCOUNT_UPDATED)).build();
    }

    @Override
    public CommonDto delete(Long id) throws AccountNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent())
            throw new AccountNotFoundException();
        accountRepository.deleteById(id);
        return CommonDto.builder().message(createCommonDtoResponseMap(Account.class.getSimpleName(), ResponseMessages.ACCOUNT_DELETED)).build();
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(AccountMapper::account2AccountDto)
                .collect(Collectors.toList());
    }

    private Map<String, String> createCommonDtoResponseMap(String key, String value) {
        return createCommonDtoResponseMap(key, value, null);
    }

    private Map<String, String> createCommonDtoResponseMap(String key, String value, Map<String, String> message) {
        if (Objects.isNull(message))
            message = new HashMap<>();
        message.put(key, value);
        return message;
    }
}
