package com.simple.banking.eterationdemo.dto;

import lombok.*;
import io.swagger.annotations.ApiModelProperty;
import com.simple.banking.eterationdemo.util.TransactionType;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @NotNull
    @NotEmpty
    @ApiModelProperty(
            value = "Amount of the transaction",
            name = "amount",
            dataType = "Double",
            example = "100.0")
    private Double amount;

    @NotNull
    @NotEmpty
    @ApiModelProperty(
            value = "Account id of the transaction",
            name = "accountId",
            dataType = "Long",
            example = "111")
    private Long accountId;

    @NotNull
    @ApiModelProperty(
            value = "Transaction type of the account",
            name = "transactionType",
            example = "DEPOSIT")
    private TransactionType transactionType;
}
