package com.tr.epay.dao.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.PaymentDAO;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;

import java.sql.*;
import java.util.Date;

public class SQLPaymentDAO implements PaymentDAO {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SELECT_ACCOUNT_BALANCE_BY_NUMBER = "SELECT balance FROM accounts WHERE account_number = ?";
    private static final String SELECT_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE = "SELECT balance FROM cards WHERE number = ? and exp_date = ?";
    private static final String UPDATE_ACCOUNT_BALANCE_BY_NUMBER = "UPDATE accounts SET balance = ? WHERE account_number = ?";
    private static final String UPDATE_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE = "UPDATE cards SET balance = ? WHERE number = ? and exp_date= ?";
    private static final String SELECT_CARD_BALANCE_BY_NUMBER = "SELECT balance FROM cards WHERE number = ?";
    private static final String UPDATE_CARD_BALANCE_BY_NUMBER = "UPDATE cards SET balance = ? WHERE number = ?";


    //insert transaction test
    private static final String INSERT_TRANSACTION = "INSERT INTO transactions (date, sender, recipient, sum, status, accounts_id, transactionTypes_id, cards_id) VALUES (?,?,?,?,?,?,?,?)";


    @Override
    public void transferFromAccountToAccount(String accountFrom, String accountTo, double sum) throws DAOException, SQLException {
        Date date = new Date();
        Timestamp dateTime = new Timestamp(date.getTime());
        Connection connection = null;

        int cardInd = 0;
        PreparedStatement preparedStatementFrom = null;
        PreparedStatement preparedStatementTo = null;
        PreparedStatement transactionPreparedStatement = null;
        ResultSet resultSetFrom = null;
        ResultSet resultSetTo = null;
        double balanceOfAccountFrom = 0;
        double balanceOfAccountTo = 0;
        try {
            connection = pool.takeConnection();

            connection.setAutoCommit(false);

            if(sum <= 0){
                throw new NumberFormatException("sum less or equals zero!");
            }

            preparedStatementFrom = connection.prepareStatement(SELECT_ACCOUNT_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(SELECT_ACCOUNT_BALANCE_BY_NUMBER);

            preparedStatementFrom.setString(1, accountFrom);
            resultSetFrom = preparedStatementFrom.executeQuery();
            while (resultSetFrom.next()) {
                balanceOfAccountFrom = resultSetFrom.getDouble(1);

            }
            double newBalanceOfAccountFrom;
            if(balanceOfAccountFrom >= sum){
                newBalanceOfAccountFrom = balanceOfAccountFrom - sum;
            } else {
                throw new SQLException("Invalid balance");
            }


            preparedStatementTo.setString(1, accountTo);
            resultSetTo = preparedStatementTo.executeQuery();
            while (resultSetTo.next()) {
                balanceOfAccountTo = resultSetTo.getDouble(1);

            }
            double newBalanceOfAccountTo = balanceOfAccountTo + sum;


            preparedStatementFrom = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_BY_NUMBER);


            preparedStatementFrom.setDouble(1, newBalanceOfAccountFrom);
            preparedStatementFrom.setString(2, accountFrom);
            preparedStatementFrom.executeUpdate();


            preparedStatementTo.setDouble(1, newBalanceOfAccountTo);
            preparedStatementTo.setString(2, accountTo);
            preparedStatementTo.executeUpdate();

            //transaction table

            transactionPreparedStatement = connection.prepareStatement(INSERT_TRANSACTION);

            transactionPreparedStatement.setTimestamp(1, dateTime);
            transactionPreparedStatement.setString(2, "change ");
            transactionPreparedStatement.setString(3,accountTo);
            transactionPreparedStatement.setDouble(4,sum);
            transactionPreparedStatement.setString(5,"executed");
            transactionPreparedStatement.setInt(6, 107); //test
            transactionPreparedStatement.setInt(7, 1); //account transaction type
            transactionPreparedStatement.setNull(8, Types.INTEGER);// must be null


            transactionPreparedStatement.executeUpdate();


            connection.commit();

        } catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();
            }
            throw new DAOException("Error during transaction between accounts", ex);

        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();

            }
            if (preparedStatementFrom != null && preparedStatementTo != null && transactionPreparedStatement != null) {
                preparedStatementFrom.close();
                preparedStatementTo.close();
                transactionPreparedStatement.close();
            }
            if (resultSetFrom != null && resultSetTo != null) {
                resultSetFrom.close();
                resultSetTo.close();
            }


        }


    }

    @Override
    public void transferFromAccountToCard(String accNumber, String cardNumber, String expireDate, double sum) throws DAOException, SQLException {
        Date date = new Date();
        Timestamp dateTime = new Timestamp(date.getTime());
        int cardIndex=0;


        Connection connection = null;
        PreparedStatement preparedStatementFrom = null;
        PreparedStatement preparedStatementTo = null;
        PreparedStatement transactionPreparedStatement = null;
        ResultSet resultSetFrom = null;
        ResultSet resultSetTo = null;
        double balanceOfAccountFrom = 0;
        double balanceOfAccountTo = 0;
        try {
            connection = pool.takeConnection();

            connection.setAutoCommit(false);

            if(sum <= 0){
                throw new NumberFormatException("sum less or equals zero!");
            }


            preparedStatementFrom = connection.prepareStatement(SELECT_ACCOUNT_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(SELECT_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE);


            preparedStatementFrom.setString(1, accNumber);
            resultSetFrom = preparedStatementFrom.executeQuery();
            while (resultSetFrom.next()) {
                balanceOfAccountFrom = resultSetFrom.getDouble(1);
            }

            double newBalanceOfAccountFrom;

            if(balanceOfAccountFrom >= sum){
                newBalanceOfAccountFrom = balanceOfAccountFrom - sum;
            } else {
                throw new SQLException("Invalid balance");
            }



            preparedStatementTo.setString(1, cardNumber);
            preparedStatementTo.setString(2, expireDate);
            resultSetTo = preparedStatementTo.executeQuery();
            while (resultSetTo.next()) {
                balanceOfAccountTo = resultSetTo.getDouble(1);
            }


            double newBalanceOfAccountTo = balanceOfAccountTo + sum;

            preparedStatementFrom = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(UPDATE_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE);


            preparedStatementFrom.setDouble(1, newBalanceOfAccountFrom);
            preparedStatementFrom.setString(2, accNumber);
            preparedStatementFrom.executeUpdate();


            preparedStatementTo.setDouble(1, newBalanceOfAccountTo);
            preparedStatementTo.setString(2, cardNumber);
            preparedStatementTo.setString(3, expireDate);
            preparedStatementTo.executeUpdate();


            //transaction table
            transactionPreparedStatement = connection.prepareStatement(INSERT_TRANSACTION);

            transactionPreparedStatement.setTimestamp(1, dateTime);
            transactionPreparedStatement.setString(2, accNumber);
            transactionPreparedStatement.setString(3,cardNumber);
            transactionPreparedStatement.setDouble(4,sum);
            transactionPreparedStatement.setString(5,"executed");
            transactionPreparedStatement.setInt(6, 1); //test
            transactionPreparedStatement.setInt(7, 1); //account transaction type
            transactionPreparedStatement.setNull(8, Types.INTEGER); // must be null

            transactionPreparedStatement.executeUpdate();


            connection.commit();



        } catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();

            }
            throw new DAOException("error during transaction between account and card", ex);

        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
            if (preparedStatementFrom != null && preparedStatementTo != null && transactionPreparedStatement != null) {
                preparedStatementFrom.close();
                preparedStatementTo.close();
                transactionPreparedStatement.close();
            }
            if (resultSetFrom != null && resultSetTo != null) {
                resultSetFrom.close();
                resultSetTo.close();
            }
        }
    }

    @Override
    public void transferFromCardToAccount(String cardNumber, String accNumber, double sum) throws DAOException, SQLException {
        Date date = new Date();
        Timestamp dateTime = new Timestamp(date.getTime());


        Connection connection = null;
        PreparedStatement preparedStatementFrom = null;
        PreparedStatement preparedStatementTo = null;
        PreparedStatement transactionPreparedStatement = null;
        ResultSet resultSetFrom = null;
        ResultSet resultSetTo = null;
        double balanceOfCardFrom = 0;
        double balanceOfAccountTo = 0;
        try {
            connection = pool.takeConnection();

            connection.setAutoCommit(false);

            if(sum <= 0){
                throw new NumberFormatException("sum less or equals zero!");
            }

            preparedStatementFrom = connection.prepareStatement(SELECT_CARD_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(SELECT_ACCOUNT_BALANCE_BY_NUMBER);


            preparedStatementFrom.setString(1, cardNumber);
            resultSetFrom = preparedStatementFrom.executeQuery();
            while (resultSetFrom.next()) {
                balanceOfCardFrom = resultSetFrom.getDouble(1);
            }

            double newBalanceOfCardFrom;
            if(balanceOfCardFrom >= sum){
                newBalanceOfCardFrom = balanceOfCardFrom - sum;
            } else {
                throw new SQLException("Invalid balance");
            }

            preparedStatementTo.setString(1, accNumber);
            resultSetTo = preparedStatementTo.executeQuery();
            while (resultSetTo.next()) {
                balanceOfAccountTo = resultSetTo.getDouble(1);
            }


            double newBalanceOfAccountTo = balanceOfAccountTo + sum;

            preparedStatementFrom = connection.prepareStatement(UPDATE_CARD_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_BY_NUMBER);


            preparedStatementFrom.setDouble(1, newBalanceOfCardFrom);
            preparedStatementFrom.setString(2, cardNumber);
            preparedStatementFrom.executeUpdate();


            preparedStatementTo.setDouble(1, newBalanceOfAccountTo);
            preparedStatementTo.setString(2, accNumber);
            preparedStatementTo.executeUpdate();


            //transaction table
            transactionPreparedStatement = connection.prepareStatement(INSERT_TRANSACTION);

            transactionPreparedStatement.setTimestamp(1, dateTime);
            transactionPreparedStatement.setString(2, cardNumber);
            transactionPreparedStatement.setString(3,accNumber);
            transactionPreparedStatement.setDouble(4,sum);
            transactionPreparedStatement.setString(5,"executed");
            transactionPreparedStatement.setNull(6, Types.INTEGER); //must be null
            transactionPreparedStatement.setInt(7, 2); //card transaction type
            transactionPreparedStatement.setInt(8, 1); // test

            transactionPreparedStatement.executeUpdate();



            connection.commit();



        } catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();

            }
            throw new DAOException("error during transaction between card and account", ex);

        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
            if (preparedStatementFrom != null && preparedStatementTo != null && transactionPreparedStatement != null) {
                preparedStatementFrom.close();
                preparedStatementTo.close();
                transactionPreparedStatement.close();
            }
            if (resultSetFrom != null && resultSetTo != null) {
                resultSetFrom.close();
                resultSetTo.close();
            }
        }
    }
}
