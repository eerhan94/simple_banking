package com.simple.banking.eterationdemo.repository;

import com.simple.banking.eterationdemo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
