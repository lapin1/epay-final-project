package com.tr.epay.entity;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String cardNumber;
    private double balance;
    private int cvv;
    private String date;

    public Card(){

    }

    public Card(int id, String cardNumber, double balance, int cvv, String date) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.cvv = cvv;
        this.date = date;
    }

    public Card(String cardNumber, double balance, int cvv, String date) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.cvv = cvv;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && Double.compare(card.balance, balance) == 0 && cvv == card.cvv && cardNumber.equals(card.cardNumber) && date.equals(card.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, balance, cvv, date);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", balance=" + balance +
                ", cvv=" + cvv +
                ", date='" + date + '\'' +
                '}';
    }
}
