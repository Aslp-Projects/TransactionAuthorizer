package com.project.transaction.authorizer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    @JsonProperty("active-card")
    boolean activeCard;
    @JsonProperty("available-limit")
    long availableLimit;

    public boolean isActiveCard() {
        return activeCard;
    }

    public void setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
    }

    public long getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(long availableLimit) {
        this.availableLimit = availableLimit;
    }
}
