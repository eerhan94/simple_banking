package com.simple.banking.eterationdemo.controller;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.junit.platform.runner.JUnitPlatform;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.util.AccountType;
import org.springframework.mock.web.MockHttpServletRequest;
import com.simple.banking.eterationdemo.dto.TransactionDto;
import com.simple.banking.eterationdemo.entity.Transaction;
import com.simple.banking.eterationdemo.util.TransactionType;
import com.simple.banking.eterationdemo.dto.AccountTransactionDto;
import com.simple.banking.eterationdemo.service.TransactionService;
import org.springframework.web.context.request.RequestContextHolder;
import com.simple.banking.eterationdemo.exception.TransactionException;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static com.simple.banking.eterationdemo.util.ResponseMessages.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @InjectMocks
    TransactionController transactionController;

    @Mock
    TransactionService transactionService;

    @Test
    void create() throws TransactionException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        TransactionDto accountDto = getTransactionDto();
        CommonDto commonDto = getCommonDto("1", TRANSACTION_COMPLETED);

        when(transactionService.create(any(TransactionDto.class))).thenReturn(commonDto);

        ResponseEntity<CommonDto> responseEntity = transactionController.create(accountDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<String, String> messages = responseEntity.getBody().getMessage();
        assertThat(messages.get(Transaction.class.getSimpleName())).isEqualTo(TRANSACTION_COMPLETED);
        assertThat(messages.get(TRANSACTION_ID_FIELD)).isNotNull();
    }

    @Test
    void getTransactions() throws TransactionException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        AccountTransactionDto accountTransactionDto = getAccountTransactionDto();

        when(transactionService.getTransactionsById(any(Long.class))).thenReturn(accountTransactionDto);

        ResponseEntity<AccountTransactionDto> responseEntity = transactionController.getTransactions(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        AccountTransactionDto response = responseEntity.getBody();
        assertThat(accountTransactionDto.getName()).isEqualTo(response.getName());
        assertThat(accountTransactionDto.getEmail()).isEqualTo(response.getEmail());
        assertThat(accountTransactionDto.getAmount()).isEqualTo(response.getAmount());
        assertThat(accountTransactionDto.getSurname()).isEqualTo(response.getSurname());
        assertThat(accountTransactionDto.getAccountType()).isEqualTo(response.getAccountType());
        assertThat(accountTransactionDto.getTransactionDetailsList().size()).isEqualTo(response.getTransactionDetailsList().size());
    }

    @Test
    void getAllTransactions() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<AccountTransactionDto> accountTransactionDtoList = new ArrayList<>();
        accountTransactionDtoList.add(getAccountTransactionDto());

        when(transactionService.getAllTransactions()).thenReturn(accountTransactionDtoList);

        ResponseEntity<List<AccountTransactionDto>> responseEntity = transactionController.getAllTransactions();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<AccountTransactionDto> response = responseEntity.getBody();
        assertThat(accountTransactionDtoList.size()).isEqualTo(response.size());
    }

    @Test
    void handleTransactionException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        TransactionException transactionException = new TransactionException();
        ResponseEntity<String> responseEntity = transactionController.handleTransactionException(transactionException);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        String response = responseEntity.getBody();
        assertThat(transactionException.getMessage()).isEqualTo(response);
    }

    private TransactionDto getTransactionDto() {
        return TransactionDto.builder()
                .accountId(1L)
                .amount(100.0)
                .transactionType(TransactionType.DEPOSIT)
                .build();
    }

    private AccountTransactionDto getAccountTransactionDto() {
        return AccountTransactionDto.builder()
                .amount(100.0)
                .accountType(AccountType.CUSTOMER)
                .transactionDetailsList(new ArrayList<>())
                .email("test@test.com")
                .surname("Karaaslan")
                .name("Eyup")
                .build();
    }

    private CommonDto getCommonDto(String id, String message) {
        HashMap<String, String> messages = new HashMap<>();
        messages.put(TRANSACTION_ID_FIELD, id);
        messages.put(Transaction.class.getSimpleName(), message);
        return CommonDto.builder().message(messages).build();
    }
}