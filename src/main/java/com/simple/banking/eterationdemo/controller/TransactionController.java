package com.simple.banking.eterationdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.dto.TransactionDto;
import com.simple.banking.eterationdemo.dto.AccountTransactionDto;
import com.simple.banking.eterationdemo.service.TransactionService;
import com.simple.banking.eterationdemo.exception.TransactionException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CommonDto> create(@RequestBody TransactionDto transactionDto) throws TransactionException {
        return ResponseEntity.ok(transactionService.create(transactionDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountTransactionDto> getTransactions(@PathVariable("id") long id) throws TransactionException {
        return ResponseEntity.ok(transactionService.getTransactionsById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountTransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<String> handleTransactionException(TransactionException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
