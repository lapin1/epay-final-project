package com.tr.epay.dao;

import com.tr.epay.entity.Transaction;

import java.util.List;

public interface TransactionDAO {

    void deleteTransaction(int id) throws DAOException;
    List<Transaction> showAccountTransactionsById(int id) throws DAOException;
    List<Transaction> showCardTransactionsById(int id) throws DAOException;
}
