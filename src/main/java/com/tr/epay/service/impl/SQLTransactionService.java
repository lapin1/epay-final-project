package com.tr.epay.service.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.dao.TransactionDAO;
import com.tr.epay.entity.Transaction;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.TransactionService;

import java.util.List;

public class SQLTransactionService implements TransactionService {
    DAOProvider provider = DAOProvider.getInstance();
    TransactionDAO transactionDAO = provider.getTransactionDAO();
    @Override
    public List<Transaction> showAccountTransactionsById(int id) throws ServiceException, DAOException {
        List<Transaction> transactions;
         transactions = transactionDAO.showAccountTransactionsById(id);
         return transactions;
    }

    @Override
    public List<Transaction> showCardTransactionsById(int id) throws ServiceException, DAOException {
        List<Transaction> transactions;
        transactions = transactionDAO.showCardTransactionsById(id);
        return transactions;
    }
}
