package com.tr.epay.service.impl;

import com.tr.epay.dao.AccountDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.entity.Account;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;

import java.util.List;

public class SQLAccountService implements AccountService {
    DAOProvider provider = DAOProvider.getInstance();
    AccountDAO accountDAO = provider.getAccountDAO();

    @Override
    public List<Account> showAccountsByUserId(int id) throws ServiceException {

        List<Account> accounts;

        try {
            accounts = accountDAO.showAccountsByUserId(id);
        }catch (DAOException ex){
            throw new ServiceException("Error show accounts", ex);
        }

        return accounts;
    }

    @Override
    public void createAccount(Account account) throws ServiceException {
        try {
            accountDAO.createAccount(account);
        }catch (DAOException ex){
            throw new ServiceException("Error creating account", ex);
        }
    }

    @Override
    public Account selectAccount(int id) throws ServiceException {
        Account account;
        try {
            account = accountDAO.selectAccount(id);
        }catch (DAOException ex){
            throw new ServiceException("Error selecting account", ex);
        }
     return account;
    }

    @Override
    public void blockAccount(int id) throws ServiceException {

        try {
             accountDAO.blockAccount(id);
        }catch (DAOException ex){
            throw new ServiceException("Error blocking account", ex);
        }

    }

    @Override
    public void unblockAccount(int id) throws ServiceException {

        try {
            accountDAO.unblockAccount(id);
        }catch (DAOException ex){
            throw new ServiceException("Error unblocking account", ex);
        }

    }
}
