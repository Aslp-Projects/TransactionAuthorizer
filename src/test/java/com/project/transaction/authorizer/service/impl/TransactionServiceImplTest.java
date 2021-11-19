package com.project.transaction.authorizer.service.impl;

import com.project.transaction.authorizer.dto.AccountOutDto;
import com.project.transaction.authorizer.dto.TransactionDto;
import com.project.transaction.authorizer.dto.entity.Account;
import com.project.transaction.authorizer.dto.entity.Transaction;
import com.project.transaction.authorizer.repositories.TransactionRepository;
import com.project.transaction.authorizer.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {
    @Mock
    AccountServiceImpl accountService;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    JsonUtils jsonUtils;
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;

    private final String createAccountInputString = "{\"account\": {\"id\": 1, \"active-card\": true, \"available-limit\": 100}}";
    private final String executeTransactionInputString = "{\"transaction\": {\"id\": 1, \"merchant\": \"Burger King\", \"amount\": 90,\"time\":\"2019-02-13T10:00:00.000Z\"}}";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRunProcess_CreateAccount() throws Exception {
        when(accountService.getAccountOutDto(any(), any())).thenReturn(new AccountOutDto());
        when(accountService.createAccountProcess(any())).thenReturn(new AccountOutDto());
        when(accountService.findAccountById(anyLong())).thenReturn(new Account());
        when(transactionRepository.findLastTransaction(anyLong())).thenReturn(new Transaction());
        when(transactionRepository.findLastTransactions(anyLong())).thenReturn(Arrays.<Transaction>asList(new Transaction()));
        // when(jsonUtils.getSpecificObject(anyString(), any(), any())).thenReturn(new T());

        AccountOutDto result = transactionServiceImpl.runProcess(createAccountInputString);
        Assert.assertEquals(new AccountOutDto().getAvailableLimit(), result.getAvailableLimit());
    }

    @Test
    public void testRunProcess_ExecuteTransaction() throws Exception {
        when(accountService.getAccountOutDto(any(), any())).thenReturn(new AccountOutDto());
        when(accountService.findAccountById(anyLong())).thenReturn(buildAccount());
        Date dateOne = new Date();
        dateOne.setTime(1000999);
        when(transactionRepository.findLastTransaction(anyLong())).thenReturn(buildTransaction(dateOne));
        when(transactionRepository.findLastTransactions(anyLong())).thenReturn(Arrays.<Transaction>asList(buildTransaction(dateOne)));
        when(jsonUtils.getSpecificObject(anyString(), any(), any())).thenReturn(buildTransactionDto(new Date()));

        AccountOutDto result = transactionServiceImpl.runProcess(executeTransactionInputString);
        Assert.assertEquals(new AccountOutDto().getAvailableLimit(), result.getAvailableLimit());
    }

    private Account buildAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setActiveCard(true);
        account.setAvailableLimit(100);
        return account;
    }

    private TransactionDto buildTransactionDto(Date date) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTime(date);
        transactionDto.setMerchant("Burger King");
        transactionDto.setId(1L);
        transactionDto.setAmount(90);
        return transactionDto;
    }

    private Transaction buildTransaction(Date date) {
        Transaction transaction = new Transaction();
        transaction.setCreatedAt(date);
        transaction.setMerchant("Frisby");
        transaction.setId(2L);
        transaction.setAmount(90);
        return transaction;
    }
}

