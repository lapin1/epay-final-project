package com.tr.epay.service.impl;

import com.tr.epay.dao.AccountDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.entity.Account;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;

import java.util.List;

public class SQLAccountService implements AccountService {
    private static final Logger log = Logger.getLogger(SQLAccountService.class);
    private final DAOProvider provider = DAOProvider.getInstance();
    private final AccountDAO accountDAO = provider.getAccountDAO();

    @Override
    public List<Account> showAccountsByUserId(int id) throws ServiceException {
        List<Account> accounts;

        try {
            accounts = accountDAO.showAccountsByUserId(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_ACCOUNT_FROM_DATABASE + id);
            throw new ServiceException(LoggerMessage.ERROR_SELECTING_ACCOUNT_FROM_DATABASE, ex);
        }

        return accounts;
    }

    @Override
    public void createAccount(Account account) throws ServiceException {
        try {
            accountDAO.createAccount(account);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_CREATING_ACCOUNT);
            throw new ServiceException(LoggerMessage.ERROR_CREATING_ACCOUNT, ex);
        }
    }

    @Override
    public Account selectAccount(int id) throws ServiceException {
        Account account;
        try {
            account = accountDAO.selectAccount(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_ACCOUNT_FROM_DATABASE + id);
            throw new ServiceException(LoggerMessage.ERROR_SELECTING_ACCOUNT_FROM_DATABASE, ex);
        }
     return account;
    }

    @Override
    public void blockAccount(int id) throws ServiceException {

        try {
             accountDAO.blockAccount(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_BLOCKING_ACCOUNT + id);
            throw new ServiceException(LoggerMessage.ERROR_BLOCKING_ACCOUNT, ex);
        }

    }

    @Override
    public void unblockAccount(int id) throws ServiceException {

        try {
            accountDAO.unblockAccount(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_UNBLOCKING_ACCOUNT + id);
            throw new ServiceException(LoggerMessage.ERROR_UNBLOCKING_ACCOUNT, ex);
        }

    }

    @Override
    public double getBalanceOfAccount(String accountNumber) throws ServiceException {
        double accountBalance;
        try {
            accountBalance = accountDAO.getBalanceOfAccount(accountNumber);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_BALANCE + accountNumber);
            throw new ServiceException(LoggerMessage.ERROR_GETTING_BALANCE, ex);
        }
        return accountBalance;
    }
}
