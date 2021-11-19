package com.project.transaction.authorizer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.transaction.authorizer.enums.EErrorState;
import com.project.transaction.authorizer.enums.EOptions;
import com.project.transaction.authorizer.dto.AccountInDto;
import com.project.transaction.authorizer.dto.AccountOutDto;
import com.project.transaction.authorizer.dto.TransactionDto;
import com.project.transaction.authorizer.dto.entity.Account;
import com.project.transaction.authorizer.dto.entity.Transaction;
import com.project.transaction.authorizer.exception.TransactionException;
import com.project.transaction.authorizer.repositories.TransactionRepository;
import com.project.transaction.authorizer.service.ITransactionService;
import com.project.transaction.authorizer.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionServiceImpl implements ITransactionService {

    AccountServiceImpl accountService;
    TransactionRepository transactionRepository;
    JsonUtils jsonUtils;

    @Override
    public AccountOutDto runProcess(String inputString) throws JsonProcessingException, TransactionException {
        if (inputString.contains(EOptions.ACCOUNT.getTransactionsOptions())) {
            AccountInDto accountInDto = jsonUtils.getSpecificObject(inputString, EOptions.ACCOUNT, AccountInDto.class);
            return accountService.createAccountProcess(accountInDto);
        } else {
            TransactionDto transactionDto = jsonUtils.getSpecificObject(inputString, EOptions.TRANSACTION, TransactionDto.class);
            return executeTransaction(transactionDto);
        }
    }

    private AccountOutDto executeTransaction(TransactionDto inputTransaction) throws TransactionException {
        Account dbAccount = accountService.findAccountById(inputTransaction.getId());
        try {
            this.checkActiveCard(dbAccount.isActiveCard());
            long lastAmount = this.subtractAmount(inputTransaction.getAmount(), dbAccount.getAvailableLimit());

            this.checkLastTransaction(inputTransaction);
            this.checkFrequencyTransactions(inputTransaction);

            saveTransaction(inputTransaction);
            dbAccount.setAvailableLimit(lastAmount);
            accountService.updateAmount(dbAccount, lastAmount);
            return accountService.getAccountOutDto(dbAccount, null);
        } catch (TransactionException e) {
            return accountService.getAccountOutDto(dbAccount, EErrorState.valueOf(e.getMessage()));
        }
    }

    private long subtractAmount(long inputAmount, long dbAmount) throws TransactionException {
        if (inputAmount > dbAmount) {
            throw new TransactionException(EErrorState.INSUFFICIENT_LIMIT);
        }
        return dbAmount - inputAmount;
    }

    private void saveTransaction(TransactionDto inputTransaction) {
        Transaction transaction = new Transaction();
        transaction.setAmount(inputTransaction.getAmount());
        transaction.setMerchant(inputTransaction.getMerchant());
        transaction.setCreatedAt(inputTransaction.getTime());
        transactionRepository.save(transaction);
    }

    private void checkActiveCard(boolean isActiveCard) throws TransactionException {
        if (!isActiveCard) {
            throw new TransactionException(EErrorState.CARD_NOT_ACTIVE);
        }
    }

    private void checkLastTransaction(TransactionDto inputTransactionDto) throws TransactionException {
        Transaction transaction = transactionRepository.findLastTransaction(inputTransactionDto.getId());
        if (transaction != null) {
            long seconds = getSecondsDiff(transaction);
            if (seconds < 120
                    && (inputTransactionDto.getId() == transaction.getId()
                    && inputTransactionDto.getMerchant().equals(transaction.getMerchant())
                    && inputTransactionDto.getAmount() == transaction.getAmount())) {
                throw new TransactionException(EErrorState.DOUBLED_TRANSACTION);
            }
        }
    }

    private long getSecondsDiff(Transaction transaction) {
        long diffInMillies = Math.abs(transaction.getCreatedAt().getTime() - new Date().getTime());
        return TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private void checkFrequencyTransactions(TransactionDto inputTransactionDto) throws TransactionException {
        List<Transaction> transactions = transactionRepository.findLastTransactions(inputTransactionDto.getId());
        for (Transaction transaction : transactions) {
            if (getSecondsDiff(transaction) < 120) {
                throw new TransactionException(EErrorState.HIGH_FREQUENCY_SMALL_INTERVAL);
            }
        }
    }

    @Autowired
    public void setAccountService(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setJsonUtils(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }
}
