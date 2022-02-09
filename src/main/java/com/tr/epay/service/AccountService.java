package com.tr.epay.service;

import com.tr.epay.entity.Account;
import java.util.List;

public interface AccountService {
    List<Account> showAccountsByUserId(int id) throws ServiceException;
    void createAccount(Account account) throws ServiceException;
    Account selectAccount(int id) throws ServiceException;
    void blockAccount(int id) throws ServiceException;
    void unblockAccount(int id) throws ServiceException;
    double getBalanceOfAccount(String accountNumber) throws ServiceException;
}
