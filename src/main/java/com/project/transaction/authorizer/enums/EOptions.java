package com.project.transaction.authorizer.enums;

public enum EOptions {

    TRANSACTION("transaction"),
    ACCOUNT("account");

    private EOptions() {
    }

    private String transactionsOptions;

    EOptions(String transactionsOptions) {
        this.transactionsOptions = transactionsOptions;
    }

    public String getTransactionsOptions() {
        return transactionsOptions;
    }
}
