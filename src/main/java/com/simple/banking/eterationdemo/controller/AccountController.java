package com.simple.banking.eterationdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import com.simple.banking.eterationdemo.dto.CommonDto;
import com.simple.banking.eterationdemo.dto.AccountDto;
import com.simple.banking.eterationdemo.service.AccountService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.simple.banking.eterationdemo.exception.AccountNotFoundException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.validation.Valid;

import static com.simple.banking.eterationdemo.util.ResponseMessages.ACCOUNT_NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonDto> create(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.create(accountDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> read(@PathVariable("id") long id) throws AccountNotFoundException {
        return ResponseEntity.ok(accountService.read(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonDto> update(@PathVariable("id") Long id, @RequestBody AccountDto accountDto) throws AccountNotFoundException {
        return ResponseEntity.ok(accountService.update(id, accountDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonDto> delete(@PathVariable("id") long id) throws AccountNotFoundException {
        return ResponseEntity.ok(accountService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleNotFoundExceptions(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ACCOUNT_NOT_FOUND);
    }
}
