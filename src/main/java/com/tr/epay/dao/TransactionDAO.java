package com.tr.epay.dao;

import com.tr.epay.entity.Transaction;

import java.util.List;

public interface TransactionDAO {

    boolean deleteTransaction(int id) throws DAOException;
    List<Transaction> showAccountTransactionsById(String accountNumber) throws DAOException;
    List<Transaction> showCardTransactionsById(String cardNumber) throws DAOException;
}
