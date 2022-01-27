package com.tr.epay.service;

import com.tr.epay.dao.DAOException;
import com.tr.epay.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> showAccountTransactionsById(int id) throws ServiceException, DAOException;
    List<Transaction> showCardTransactionsById(int id) throws ServiceException, DAOException;
}
