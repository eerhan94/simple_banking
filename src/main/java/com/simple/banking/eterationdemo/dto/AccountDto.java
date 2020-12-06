package com.simple.banking.eterationdemo.dto;

import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.simple.banking.eterationdemo.util.AccountType;

import javax.validation.constraints.*;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @NotNull
    @NotEmpty
    @ApiModelProperty(
            value = "First name of the account",
            name = "name",
            dataType = "String",
            example = "Eyup")
    private String name;

    @NotNull
    @NotEmpty
    @ApiModelProperty(
            value = "Surname of the account",
            name = "surname",
            dataType = "String",
            example = "Karaaslan")
    private String surname;

    @Email
    @NotNull
    @NotEmpty
    @ApiModelProperty(
            value = "Email of the account",
            name = "email",
            dataType = "String",
            example = "eyuperhankaraaslan94@gmail.com")
    private String email;

    @NotNull
    @ApiModelProperty(
            value = "Account type of the account",
            name = "accountType",
            example = "CUSTOMER")
    private AccountType accountType;
}
