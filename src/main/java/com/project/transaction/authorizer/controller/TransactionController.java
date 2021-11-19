package com.project.transaction.authorizer.controller;

import com.project.transaction.authorizer.dto.entity.Account;
import com.project.transaction.authorizer.service.IAccountService;
import com.project.transaction.authorizer.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("transaction")
public class TransactionController {

    ITransactionService transactionService;
    IAccountService accountService;

    @PostMapping()
    public ResponseEntity<Void> transaction(String input){
        try {
            transactionService.runProcess(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @Autowired
    public void setTransactionService(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }
}
