package com.simple.banking.eterationdemo.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.annotations.ApiModel;
import com.simple.banking.eterationdemo.util.AccountType;

import java.util.List;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionDto {
    private String name;
    private String email;
    private Double amount;
    private String surname;
    private AccountType accountType;
    private List<TransactionDetails> transactionDetailsList;
}
