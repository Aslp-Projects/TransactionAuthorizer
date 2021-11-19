package com.project.transaction.authorizer.exception;

import com.project.transaction.authorizer.enums.EErrorState;

public class TransactionException extends Exception {

    public TransactionException(EErrorState errorState) {
        super(errorState.name());
    }
}
