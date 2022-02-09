package com.tr.epay.dao;

import com.tr.epay.entity.Account;
import java.util.List;

public interface AccountDAO {
    List<Account> showAccountsByUserId(int id) throws DAOException;
    void createAccount(Account account) throws DAOException;
    Account selectAccount(int id) throws DAOException;
    void blockAccount(int id) throws DAOException;
    void unblockAccount(int id) throws DAOException;
    double getBalanceOfAccount(String accountNumber) throws DAOException;
}
