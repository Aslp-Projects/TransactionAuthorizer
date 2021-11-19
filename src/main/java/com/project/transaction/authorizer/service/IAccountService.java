package com.project.transaction.authorizer.service;

import com.project.transaction.authorizer.enums.EErrorState;
import com.project.transaction.authorizer.dto.AccountInDto;
import com.project.transaction.authorizer.dto.AccountOutDto;
import com.project.transaction.authorizer.dto.entity.Account;
import com.project.transaction.authorizer.exception.TransactionException;

import java.util.List;

public interface IAccountService {
    AccountOutDto getAccountOutDto(Account dbAccount, EErrorState errorState);

    Account saveAccount(AccountInDto inputDto);

    void updateAmount(Account dbAccount, long amount);

    AccountOutDto createAccountProcess(AccountInDto inputDto);

    Account findAccountById(long id) throws TransactionException;

    List<Account> getAccounts();
}
