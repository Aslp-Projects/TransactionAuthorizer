package com.project.transaction.authorizer.dto;

import com.project.transaction.authorizer.enums.EErrorState;

import java.util.Arrays;

public class AccountOutDto extends AccountDto {

    String[] violations = {};

    public String[] getViolations() {
        return violations;
    }

    public void setViolations(EErrorState violations) {
        this.violations = new String[]{violations.getErrorState()};
    }

    @Override
    public String toString() {
        return "[\"account\"{" +
                "\"active-card\"=" + activeCard +
                ", \"available-limit\"=" + availableLimit +
                ", \"violations\"=" + (Arrays.toString(violations != null ? violations : new String[]{""})) +
                '}' + "]";
    }
}
