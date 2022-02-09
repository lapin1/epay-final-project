package com.tr.epay.service.impl;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.dao.TransactionDAO;
import com.tr.epay.entity.Transaction;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.TransactionService;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;

import java.util.List;

public class SQLTransactionService implements TransactionService {
    private static final Logger log = Logger.getLogger(SQLTransactionService.class);
    private final DAOProvider provider = DAOProvider.getInstance();

    @Override
    public List<Transaction> showAccountTransactionsById(String accNumber) throws ServiceException{
        List<Transaction> transactions;
        try {
            TransactionDAO transactionDAO = provider.getTransactionDAO();
            transactions = transactionDAO.showAccountTransactionsById(accNumber);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SHOWING_ACCOUNT_TRANSACTIONS, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

         return transactions;
    }

    @Override
    public List<Transaction> showCardTransactionsById(String cardNumber) throws ServiceException{
        List<Transaction> transactions;
        try {
            TransactionDAO transactionDAO = provider.getTransactionDAO();
            transactions = transactionDAO.showCardTransactionsById(cardNumber);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SHOWING_CARD_TRANSACTIONS, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        return transactions;
    }
}
