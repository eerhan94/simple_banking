package com.simple.banking.eterationdemo.entity;

import lombok.*;
import com.simple.banking.eterationdemo.util.AccountType;

import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Column(length = 100, name = "name")
    private String name;

    @Column(length = 100, name = "email")
    private String email;

    @Column(length = 100, name = "amount")
    private Double amount;

    @Column(length = 100, name = "surname")
    private String surname;

    @Column(length = 100, name = "accountType")
    private AccountType accountType;

    @OneToMany
    @JoinColumn(name = "account_transactions")
    private List<Transaction> transactionList;
}
