package com.project.transaction.authorizer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.transaction.authorizer.dto.AccountOutDto;
import com.project.transaction.authorizer.exception.TransactionException;

public interface ITransactionService {

    AccountOutDto runProcess(String input) throws JsonProcessingException, TransactionException;
}
