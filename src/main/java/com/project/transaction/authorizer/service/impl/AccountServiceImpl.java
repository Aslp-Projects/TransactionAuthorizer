package com.project.transaction.authorizer.service.impl;

import com.project.transaction.authorizer.enums.EErrorState;
import com.project.transaction.authorizer.dto.AccountInDto;
import com.project.transaction.authorizer.dto.AccountOutDto;
import com.project.transaction.authorizer.dto.entity.Account;
import com.project.transaction.authorizer.exception.TransactionException;
import com.project.transaction.authorizer.repositories.AccountRepository;
import com.project.transaction.authorizer.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;

    @Override
    public AccountOutDto getAccountOutDto(Account dbAccount, EErrorState errorState) {
        AccountOutDto accountOutDto = new AccountOutDto();
        accountOutDto.setActiveCard(dbAccount.isActiveCard());
        accountOutDto.setAvailableLimit(dbAccount.getAvailableLimit());
        if (errorState != null) {
            accountOutDto.setViolations(errorState);
        }
        return accountOutDto;
    }

    @Override
    public Account saveAccount(AccountInDto inpuntDto) {
        Account account = new Account();
        account.setId(inpuntDto.getId());
        account.setActiveCard(inpuntDto.isActiveCard());
        account.setAvailableLimit(inpuntDto.getAvailableLimit());
        accountRepository.save(account);
        return account;
    }

    @Override
    public void updateAmount(Account dbAccount, long amount) {
        dbAccount.setAvailableLimit(amount);
        accountRepository.save(dbAccount);
    }

    @Override
    public AccountOutDto createAccountProcess(AccountInDto inputDto) {
        Account account;
        try {
            account = findAccountById(inputDto.getId());
            return getAccountOutDto(account, EErrorState.ACCOUNT_ALREADY_INITIALIZED);
        } catch (TransactionException e) {
            account = saveAccount(inputDto);
            return getAccountOutDto(account, null);
        }
    }

    @Override
    public Account findAccountById(long id) throws TransactionException {
        return accountRepository.findById(id).orElseThrow(() -> new TransactionException(EErrorState.ACCOUNT_NOT_INITIALIZED));
    }

    @Override
    public List<Account> getAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
