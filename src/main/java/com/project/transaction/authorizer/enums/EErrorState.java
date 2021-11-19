package com.project.transaction.authorizer.enums;

public enum EErrorState {

    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
    ACCOUNT_NOT_INITIALIZED("account-not-initialized"),
    CARD_NOT_ACTIVE("card-not-active"),
    INSUFFICIENT_LIMIT("insufficient-limit"),
    HIGH_FREQUENCY_SMALL_INTERVAL("high-frequency-small-interval"),
    DOUBLED_TRANSACTION("doubled-transaction");

    private String errorState;

    EErrorState(String errorState) {
        this.errorState = errorState;
    }

    public String getErrorState() {
        return errorState;
    }
}
