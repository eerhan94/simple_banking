package com.simple.banking.eterationdemo.repository;

import com.simple.banking.eterationdemo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}