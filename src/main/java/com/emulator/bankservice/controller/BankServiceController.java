package com.emulator.bankservice.controller;

import com.emulator.bankservice.controller.request.AuthMethod;
import com.emulator.bankservice.controller.response.UserTransactionDto;
import com.emulator.bankservice.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankServiceController {

    private final BankService bankService;


    @GetMapping("/user/check")
    public ResponseEntity<String> checkIfUserExists(@RequestParam String cardNumber) {
        return ResponseEntity.ok(bankService.checkIfUserExists(cardNumber));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<UserTransactionDto>> getTransactionDtoList(@RequestParam String accountNumber,
                                                                          @RequestParam Instant startDate,
                                                                          @RequestParam Instant endDate) {
        return ResponseEntity.ok(
                bankService.getTransactionsByAccountNumberAndDateBetween(accountNumber, startDate, endDate));
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> checkBalance(@RequestParam String accountNumber) {
        return ResponseEntity.ok(bankService.getBalance(accountNumber));
    }

    @PostMapping("/deposit")
    Object deposit(@RequestParam BigDecimal amount,
                   @RequestParam String accountNumber) {
        return ResponseEntity.ok(bankService.deposit(amount, accountNumber));
    }

    @PostMapping("/withdraw")
    Object withdraw(@RequestParam BigDecimal amount,
                    @RequestParam String accountNumber) {
        return ResponseEntity.ok(bankService.withdraw(amount, accountNumber));
    }


    @PostMapping("/auth")
    ResponseEntity<Boolean> authorization(@RequestParam String credential,
                                          @RequestParam AuthMethod authMethod,
                                          @RequestParam String accountNumber) {
        return ResponseEntity.ok(bankService.auth(credential, authMethod, accountNumber));
    }
}
