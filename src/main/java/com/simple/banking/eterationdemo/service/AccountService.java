package com.simple.banking.eterationdemo.service;

import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.dto.AccountDto;
import com.simple.banking.eterationdemo.exception.AccountNotFoundException;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAll();

    CommonDto create(AccountDto accountDto);

    AccountDto read(Long id) throws AccountNotFoundException;

    CommonDto delete(Long id) throws AccountNotFoundException;

    CommonDto update(Long id, AccountDto accountDto) throws AccountNotFoundException;
}
