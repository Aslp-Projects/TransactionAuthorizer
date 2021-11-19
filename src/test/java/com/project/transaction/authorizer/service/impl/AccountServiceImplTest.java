package com.project.transaction.authorizer.service.impl;

import com.project.transaction.authorizer.dto.AccountInDto;
import com.project.transaction.authorizer.dto.AccountOutDto;
import com.project.transaction.authorizer.dto.entity.Account;
import com.project.transaction.authorizer.enums.EErrorState;
import com.project.transaction.authorizer.exception.TransactionException;
import com.project.transaction.authorizer.repositories.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAccountOutDto() {
        AccountOutDto result = accountServiceImpl.getAccountOutDto(new Account(), null);
        Assert.assertEquals(0, result.getAvailableLimit());
    }

    @Test
    public void testGetAccountOutDto_Already_Initialized() {
        AccountOutDto result = accountServiceImpl.getAccountOutDto(new Account(), EErrorState.ACCOUNT_ALREADY_INITIALIZED);
        Assert.assertEquals(0, result.getAvailableLimit());
    }

    @Test
    public void testSaveAccount() {
        Account result = accountServiceImpl.saveAccount(new AccountInDto());
        Assert.assertEquals(0, result.getAvailableLimit());
    }

    @Test
    public void testUpdateAmount() {
        accountServiceImpl.updateAmount(new Account(), 0L);
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    public void testCreateAccountProcess() {
        AccountOutDto result = accountServiceImpl.createAccountProcess(new AccountInDto());
        Assert.assertFalse(result.isActiveCard());
        Assert.assertEquals(0, result.getAvailableLimit());
    }

    @Test
    public void testCreateAccountProcess_AccountAlreadyInitialized() {
        when(accountRepository.findById(any())).thenReturn(Optional.of(new Account()));
        AccountOutDto result = accountServiceImpl.createAccountProcess(new AccountInDto());
        Assert.assertFalse(result.isActiveCard());
        Assert.assertEquals(EErrorState.ACCOUNT_ALREADY_INITIALIZED.getErrorState(), result.getViolations()[0]);
    }

    @Test
    public void testFindAccountById() throws Exception {
        when(accountRepository.findById(any())).thenReturn(Optional.of(new Account()));
        Account result = accountServiceImpl.findAccountById(0L);
        Assert.assertFalse(result.isActiveCard());
    }

    @Test(expected = TransactionException.class)
    public void testFindAccountById_Error() throws Exception {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        accountServiceImpl.findAccountById(0L);
    }

    @Test
    public void testGetAccounts() {
        List<Account> result = accountServiceImpl.getAccounts();
        Assert.assertEquals(Arrays.<Account>asList(), result);
        verify(accountRepository, times(1)).findAll();
    }
}

