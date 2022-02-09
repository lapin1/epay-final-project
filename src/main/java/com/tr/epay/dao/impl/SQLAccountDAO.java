package com.tr.epay.dao.impl;

import com.tr.epay.dao.AccountDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.entity.Account;
import com.tr.epay.utils.ErrorMessage;
import org.apache.log4j.Logger;
import com.tr.epay.utils.LoggerMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLAccountDAO implements AccountDAO {
    private static final Logger log = Logger.getLogger(SQLAccountDAO.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private static final String SELECT_ACCOUNT_BY_USER_ID = "SELECT * FROM accounts WHERE users_id = ?";
    private static final String CREATE_ACCOUNT = "INSERT INTO accounts (account_number, balance, status, users_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ACCOUNT_BY_ID = "SELECT id, account_number, balance, status, users_id FROM accounts WHERE id =?";
    private static final String BLOCK_ACCOUNT = "UPDATE accounts SET status = ? WHERE id = ?";
    private static final String UNBLOCK_ACCOUNT = "UPDATE accounts SET status = ? WHERE id = ?";
    private static final String SELECT_ACCOUNT_BALANCE_BY_NUMBER = "SELECT balance FROM accounts WHERE account_number = ?";

    @Override
    public List<Account> showAccountsByUserId(int id) throws DAOException {
        List<Account> accounts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account;
        try{
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_USER_ID);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                account = new Account();
                account.setId(resultSet.getInt(1));
                account.setAccountNumber(resultSet.getString(2));
                account.setBalance(resultSet.getDouble(3));
                account.setStatus(resultSet.getString(4));
                account.setUserId(resultSet.getInt(5));

                accounts.add(account);

            }

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_SELECTING_ACCOUNT_FROM_DATABASE + id, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return accounts;
    }

    @Override
    public void createAccount(Account account) throws DAOException {
        String accountNumber = String.valueOf((int) (1000000 + (Math.random() * 999999)));
        double accountBalance = 0;
        String accountStatus= "Active";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try { connection = pool.takeConnection();
              preparedStatement = connection.prepareStatement(CREATE_ACCOUNT);

            preparedStatement.setString(1, accountNumber);
            preparedStatement.setDouble(2, accountBalance);
            preparedStatement.setString(3, accountStatus);
            preparedStatement.setInt(4, account.getUserId());

            preparedStatement.executeUpdate();

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_CREATING_ACCOUNT + account.getAccountNumber(), ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement);
            }
        }

    }

    @Override
    public Account selectAccount(int accountId) throws DAOException {
        Account account = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID);
            preparedStatement.setInt(1, accountId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String number = resultSet.getString(2);
                double balance = resultSet.getDouble(3);
                String status = resultSet.getString(4);
                int userId = resultSet.getInt(5);
                account = new Account(id, number, balance, status, userId);
            }


        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_SELECTING_ACCOUNT_FROM_DATABASE + accountId, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return account;
    }

    @Override
    public void blockAccount(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String blockedStatus = "BLOCKED";

        try {
            connection = pool.takeConnection();

            preparedStatement = connection.prepareStatement(BLOCK_ACCOUNT);
            preparedStatement.setString(1, blockedStatus);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_BLOCKING_ACCOUNT + id, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        } finally {
            if (connection != null) {
                pool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void unblockAccount(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String activeStatus = "Active";
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(UNBLOCK_ACCOUNT);
            preparedStatement.setString(1, activeStatus);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_UNBLOCKING_ACCOUNT + id, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        } finally {
            if (connection != null) {
                pool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public double getBalanceOfAccount(String accountNumber) throws DAOException {
        double accountBalance = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BALANCE_BY_NUMBER);
            preparedStatement.setString(1, accountNumber);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                accountBalance = resultSet.getDouble(1);

            }

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_GETTING_BALANCE + accountNumber, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return accountBalance;
    }
}
