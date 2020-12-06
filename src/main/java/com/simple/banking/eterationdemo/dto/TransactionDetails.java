package com.simple.banking.eterationdemo.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.annotations.ApiModel;
import com.simple.banking.eterationdemo.util.TransactionType;

import java.util.Date;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetails {
    private Long id;
    private Double newAmount;
    private Double oldAmount;
    private Date creationDate;
    private TransactionType transactionType;
}
