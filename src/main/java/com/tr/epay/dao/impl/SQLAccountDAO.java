package com.tr.epay.dao.impl;

import com.tr.epay.dao.AccountDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLAccountDAO implements AccountDAO {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SELECT_ACCOUNT_BY_USER_ID = "select * from accounts where users_id = ?";
    private static final String CREATE_ACCOUNT = "INSERT INTO accounts (account_number, balance, status, users_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ACCOUNT_BY_ID = "SELECT id, account_number, balance, status, users_id from accounts where id =?";
    private static final String BLOCK_ACCOUNT = "UPDATE accounts SET status = ? WHERE id = ?";

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
            throw new DAOException("Error querying account from database", ex);
        }finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) { /* Ignored */}
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) { /* Ignored */}
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) { /* Ignored */}
                }
            }
        return accounts;
    }

    @Override
    public void createAccount(Account account) throws DAOException {
        String number = String.valueOf((int) (1000000 + (Math.random() * 10000000)));
        //String number = "3483259239";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try { connection = pool.takeConnection();
              preparedStatement = connection.prepareStatement(CREATE_ACCOUNT);

            preparedStatement.setString(1, number);
            preparedStatement.setDouble(2, 0);
            preparedStatement.setString(3, "Active");
            preparedStatement.setInt(4, account.getUserId());

            preparedStatement.executeUpdate();

        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException("Error creating account", ex);
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* Ignored */}
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
            throw new DAOException("Error selecting account", ex);
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
        return account;
    }

    @Override
    public void blockAccount(int id) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = pool.takeConnection();

            preparedStatement = connection.prepareStatement(BLOCK_ACCOUNT);


            preparedStatement.setString(1, "BLOCKED");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("statusUpdated blocked");

        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException("Error blocking account", ex);
        }
        try {
            preparedStatement.close();
        } catch (SQLException e) { /* Ignored */}
        try {
            connection.close();
        } catch (SQLException e) { /* Ignored */}


    }

    @Override
    public void unblockAccount(int id) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(BLOCK_ACCOUNT);

            preparedStatement.setString(1, "Active");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("statusUpdated active");

        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException("Error unblocking account", ex);
        }try {
            preparedStatement.close();
        } catch (SQLException e) { /* Ignored */}
        try {
            connection.close();
        } catch (SQLException e) { /* Ignored */}
    }
}
