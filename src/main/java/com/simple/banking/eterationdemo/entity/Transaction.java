package com.simple.banking.eterationdemo.entity;

import lombok.*;
import com.simple.banking.eterationdemo.util.TransactionType;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Column(name = "afterTransaction")
    private Double newAmount;

    @Column(name = "beforeTransaction")
    private Double oldAmount;

    @ManyToOne
    @JoinColumn(name = "user_address_id")
    private Account account;

    @Column(name = "transactionType")
    private TransactionType transactionType;
}
