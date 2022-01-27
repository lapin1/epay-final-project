package com.tr.epay.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Timestamp date;
    private String sender;
    private String recipient;
    private double sum;
    private String status;
    private int accountId;
    private int transactionTypeId;

    public Transaction(){

    }

    public Transaction(int id, Timestamp date, String sender, String recipient, double sum, String status, int accountId, int transactionTypeId) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
        this.status = status;
        this.accountId = accountId;
        this.transactionTypeId = transactionTypeId;
    }

    public Transaction(Timestamp date, String sender, String recipient, double sum, String status, int accountId, int transactionTypeId) {
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
        this.status = status;
        this.accountId = accountId;
        this.transactionTypeId = transactionTypeId;
    }

    public Transaction(Timestamp date, String sender, String recipient, double sum, String status, int accountId) {
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
        this.status = status;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setTimestamp(Timestamp date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Double.compare(that.sum, sum) == 0 && accountId == that.accountId && transactionTypeId == that.transactionTypeId && date.equals(that.date) && sender.equals(that.sender) && recipient.equals(that.recipient) && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, sender, recipient, sum, status, accountId, transactionTypeId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", sum=" + sum +
                ", status='" + status + '\'' +
                ", accountId=" + accountId +
                ", transactionTypeId=" + transactionTypeId +
                '}';
    }
}
