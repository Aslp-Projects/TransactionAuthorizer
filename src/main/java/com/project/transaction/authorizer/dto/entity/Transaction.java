package com.project.transaction.authorizer.dto.entity;

import com.project.transaction.authorizer.dto.TransactionDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String merchant;
    private long amount;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "account_id")
    @JoinColumn(name = "account_id",
            referencedColumnName = "id",
            nullable = false)
    public long accountId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
    }
}
