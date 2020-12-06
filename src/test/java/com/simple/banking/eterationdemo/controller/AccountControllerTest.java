package com.simple.banking.eterationdemo.controller;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.junit.platform.runner.JUnitPlatform;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.entity.Account;
import com.simple.banking.eterationdemo.dto.AccountDto;
import com.simple.banking.eterationdemo.util.AccountType;
import com.simple.banking.eterationdemo.dto.TransactionDto;
import org.springframework.mock.web.MockHttpServletRequest;
import com.simple.banking.eterationdemo.service.AccountService;
import org.springframework.validation.BeanPropertyBindingResult;
import com.simple.banking.eterationdemo.service.TransactionService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.simple.banking.eterationdemo.exception.AccountNotFoundException;

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
class AccountControllerTest {
    @InjectMocks
    AccountController accountController;

    @Mock
    AccountService accountService;

    @Test
    void create() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        AccountDto accountDto = getAccountDto();
        CommonDto commonDto = getCommonDto("1", ACCOUNT_CREATED);

        when(accountService.create(any(AccountDto.class))).thenReturn(commonDto);

        ResponseEntity<CommonDto> responseEntity = accountController.create(accountDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<String, String> messages = responseEntity.getBody().getMessage();
        assertThat(messages.get(Account.class.getSimpleName())).isEqualTo(ACCOUNT_CREATED);
        assertThat(messages.get(ACCOUNT_ID_FIELD)).isNotNull();
    }


    @Test
    void read() throws AccountNotFoundException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        AccountDto accountDto = getAccountDto();

        when(accountService.read(any(Long.class))).thenReturn(accountDto);

        ResponseEntity<AccountDto> responseEntity = accountController.read(1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        AccountDto response = responseEntity.getBody();
        assertThat(accountDto.getName()).isEqualTo(response.getName());
        assertThat(accountDto.getEmail()).isEqualTo(response.getEmail());
        assertThat(accountDto.getSurname()).isEqualTo(response.getSurname());
        assertThat(accountDto.getAccountType()).isEqualTo(response.getAccountType());
    }

    @Test
    void update() throws AccountNotFoundException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        AccountDto accountDto = getAccountDto();
        CommonDto commonDto = getCommonDto("1", ACCOUNT_UPDATED);

        when(accountService.update(any(Long.class), any(AccountDto.class))).thenReturn(commonDto);

        ResponseEntity<CommonDto> responseEntity = accountController.update((long) 1, accountDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<String, String> messages = responseEntity.getBody().getMessage();
        assertThat(messages.get(Account.class.getSimpleName())).isEqualTo(ACCOUNT_UPDATED);
        assertThat(messages.get(ACCOUNT_ID_FIELD)).isNotNull();
    }

    @Test
    void delete() throws AccountNotFoundException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CommonDto commonDto = getCommonDto("1", ACCOUNT_DELETED);

        when(accountService.delete(any(Long.class))).thenReturn(commonDto);

        ResponseEntity<CommonDto> responseEntity = accountController.delete(1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<String, String> messages = responseEntity.getBody().getMessage();
        assertThat(messages.get(Account.class.getSimpleName())).isEqualTo(ACCOUNT_DELETED);
        assertThat(messages.get(ACCOUNT_ID_FIELD)).isNotNull();
    }

    @Test
    void findAll() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(getAccountDto());

        when(accountService.findAll()).thenReturn(accountDtoList);

        ResponseEntity<List<AccountDto>> responseEntity = accountController.findAll();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<AccountDto> response = responseEntity.getBody();
        assertThat(accountDtoList.size()).isEqualTo(response.size());
    }

    @Test
    void handleValidationExceptions() throws NoSuchMethodException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult("testBean", "testBean");
        MethodParameter methodParameter = new MethodParameter(TransactionService.class.getMethod("create", TransactionDto.class), 0);
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(methodParameter, beanPropertyBindingResult);

        ResponseEntity<Map<String, String>> responseEntity = accountController.handleValidationExceptions(methodArgumentNotValidException);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleNotFoundExceptions() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        AccountNotFoundException accountNotFoundException = new AccountNotFoundException();
        ResponseEntity<String> responseEntity = accountController.handleNotFoundExceptions(accountNotFoundException);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        String response = responseEntity.getBody();
        assertThat(accountNotFoundException.getMessage()).isEqualTo(response);
    }

    private CommonDto getCommonDto(String id, String message) {
        HashMap<String, String> messages = new HashMap<>();
        messages.put(ACCOUNT_ID_FIELD, id);
        messages.put(Account.class.getSimpleName(), message);
        return CommonDto.builder().message(messages).build();
    }

    private AccountDto getAccountDto() {
        return AccountDto.builder()
                .name("Eyup")
                .surname("Karaaslan")
                .email("eyuperhankaraaslan94@gmail.com")
                .accountType(AccountType.CUSTOMER)
                .build();
    }
}