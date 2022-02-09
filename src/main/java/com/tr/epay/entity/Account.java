package com.tr.epay.entity;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String accountNumber;
    private double balance;
    private String status;
    private int userId;

    public Account() {

    }

    public Account(int id, String accountNumber, double balance, String status, int userId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.userId = userId;
    }

    public Account(String accountNumber, double balance, String status, int userId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Double.compare(account.balance, balance) == 0 && userId == account.userId && accountNumber.equals(account.accountNumber) && status.equals(account.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, balance, status, userId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}

