package com.tr.epay.dao.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.TransactionDAO;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLTransactionDAO implements TransactionDAO {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SHOW_ACCOUNT_TRANSACTIONS_BY_ID = "select date,sender,recipient,sum,status,accounts_id from transactions where accounts_id = ?";
    private static final String SHOW_CARD_TRANSACTIONS_BY_ID = "select date,sender,recipient,sum,status,cards_id from transactions where cards_id = ?";


    @Override
    public void deleteTransaction(int id) throws DAOException {

    }

    @Override
    public List<Transaction> showAccountTransactionsById(int id) throws DAOException {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ACCOUNT_TRANSACTIONS_BY_ID);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                transaction = new Transaction();
                transaction.setTimestamp(resultSet.getTimestamp(1));
                transaction.setSender(resultSet.getString(2));
                transaction.setRecipient(resultSet.getString(3));
                transaction.setSum(resultSet.getDouble(4));
                transaction.setStatus(resultSet.getString(5));
                transaction.setAccountId(resultSet.getInt(6));

                transactions.add(transaction);

            }


        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException("Error querying cards from database", ex);
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
        return transactions;

    }

    @Override
    public List<Transaction> showCardTransactionsById(int id) throws DAOException {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_CARD_TRANSACTIONS_BY_ID);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                transaction = new Transaction();
                transaction.setTimestamp(resultSet.getTimestamp(1));
                transaction.setSender(resultSet.getString(2));
                transaction.setRecipient(resultSet.getString(3));
                transaction.setSum(resultSet.getDouble(4));
                transaction.setStatus(resultSet.getString(5));
                transaction.setAccountId(resultSet.getInt(6));

                transactions.add(transaction);

            }


        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException("Error querying cards from database", ex);
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
        return transactions;
    }
}
