package com.tr.epay.service;

import com.tr.epay.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> showAccountTransactionsById(String accNumber) throws ServiceException;
    List<Transaction> showCardTransactionsById(String cardNumber) throws ServiceException;
}
