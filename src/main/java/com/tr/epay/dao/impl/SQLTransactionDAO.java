package com.tr.epay.dao.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.TransactionDAO;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.entity.Transaction;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLTransactionDAO implements TransactionDAO {
    private static final Logger log = Logger.getLogger(SQLTransactionDAO.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private static final String SHOW_ACCOUNT_TRANSACTIONS = "SELECT date,sender,recipient,sum,status,accounts_id FROM transactions WHERE sender = ?";
    private static final String SHOW_CARD_TRANSACTIONS = "SELECT date,sender,recipient,sum,status,cards_id FROM transactions WHERE sender = ?";
    private static final String DELETE_TRANSACTION = "DELETE FROM transactions WHERE id = ?";


    @Override
    public boolean deleteTransaction(int id) throws DAOException {
        boolean isDeleted;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_TRANSACTION);
            preparedStatement.setInt(1, id);

            isDeleted = preparedStatement.executeUpdate() > 0;

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_DELETING_TRANSACTION + id);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        } finally {
            if (connection != null) {
                pool.closeConnection(connection, preparedStatement);
            }
        }
        return isDeleted;
    }

    @Override
    public List<Transaction> showAccountTransactionsById(String accountNumber) throws DAOException {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_ACCOUNT_TRANSACTIONS);

            preparedStatement.setString(1, accountNumber);
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
           log.error(LoggerMessage.ERROR_SHOWING_ACCOUNT_TRANSACTIONS + accountNumber);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return transactions;

    }

    @Override
    public List<Transaction> showCardTransactionsById(String cardNumber) throws DAOException {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_CARD_TRANSACTIONS);

            preparedStatement.setString(1, cardNumber);
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
            log.error(LoggerMessage.ERROR_SHOWING_CARD_TRANSACTIONS + cardNumber);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return transactions;
    }
}
