package com.tr.epay.dao.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.PaymentDAO;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.Date;

public class SQLPaymentDAO implements PaymentDAO {
    private static final Logger log = Logger.getLogger(SQLPaymentDAO.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private static final String SELECT_ACCOUNT_BALANCE_BY_NUMBER = "SELECT balance FROM accounts WHERE account_number = ?";
    private static final String SELECT_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE = "SELECT balance FROM cards WHERE number = ? and exp_date = ?";
    private static final String UPDATE_ACCOUNT_BALANCE_BY_NUMBER = "UPDATE accounts SET balance = ? WHERE account_number = ?";
    private static final String UPDATE_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE = "UPDATE cards SET balance = ? WHERE number = ? and exp_date= ?";
    private static final String SELECT_CARD_BALANCE_BY_NUMBER = "SELECT balance FROM cards WHERE number = ?";
    private static final String UPDATE_CARD_BALANCE_BY_NUMBER = "UPDATE cards SET balance = ? WHERE number = ?";
    private static final String INSERT_TRANSACTION = "INSERT INTO transactions (date, sender, recipient, sum, status, accounts_id, transactionTypes_id, cards_id) VALUES (?,?,?,?,?,?,?,?)";
    private static final String STATUS = "executed";

    @Override
    public void transferFromAccountToAccount(String accountFrom, String accountTo, double sum) throws DAOException, SQLException {
        Date date = new Date();
        Timestamp dateTime = new Timestamp(date.getTime());
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
                log.error(ErrorMessage.INCORRECT_SUM);
                throw new NumberFormatException(ErrorMessage.SUM_LESS_OR_EQUALS);
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
                log.error(ErrorMessage.INCORRECT_BALANCE);
                throw new SQLException(ErrorMessage.ERROR_WITH_DATABASE);
            }

            preparedStatementTo.setString(1, accountTo);
            resultSetTo = preparedStatementTo.executeQuery();
            while (resultSetTo.next()) {
                balanceOfAccountTo = resultSetTo.getDouble(1);

            }
            System.out.println(resultSetTo.next());
            double newBalanceOfAccountTo = balanceOfAccountTo + sum;


            preparedStatementFrom = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_BY_NUMBER);
            preparedStatementFrom.setDouble(1, newBalanceOfAccountFrom);
            preparedStatementFrom.setString(2, accountFrom);
            preparedStatementFrom.executeUpdate();
            preparedStatementTo.setDouble(1, newBalanceOfAccountTo);
            preparedStatementTo.setString(2, accountTo);
            preparedStatementTo.executeUpdate();




            transactionPreparedStatement = connection.prepareStatement(INSERT_TRANSACTION);

            transactionPreparedStatement.setTimestamp(1, dateTime);
            transactionPreparedStatement.setString(2, accountFrom);
            transactionPreparedStatement.setString(3,accountTo);
            transactionPreparedStatement.setDouble(4,sum);
            transactionPreparedStatement.setString(5,STATUS);
            transactionPreparedStatement.setInt(6, 1);
            transactionPreparedStatement.setInt(7, 1);
            transactionPreparedStatement.setNull(8, Types.INTEGER);


            transactionPreparedStatement.executeUpdate();
            connection.commit();

        } catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();
            }
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_ACCOUNTS, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

        } finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatementFrom, preparedStatementTo, transactionPreparedStatement, resultSetFrom, resultSetTo);
            }

        }


    }

    @Override
    public void transferFromAccountToCard(String accNumber, String cardNumber, String expireDate, double sum) throws DAOException, SQLException {
        Date date = new Date();
        Timestamp dateTime = new Timestamp(date.getTime());

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
                log.error(ErrorMessage.INCORRECT_SUM);
                throw new NumberFormatException(ErrorMessage.SUM_LESS_OR_EQUALS);
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
                log.error(ErrorMessage.INCORRECT_BALANCE);
                throw new SQLException(ErrorMessage.INCORRECT_BALANCE);
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
            transactionPreparedStatement.setString(5, STATUS);
            transactionPreparedStatement.setInt(6, 1); //test
            transactionPreparedStatement.setInt(7, 1); //account transaction type
            transactionPreparedStatement.setNull(8, Types.INTEGER); // must be null

            transactionPreparedStatement.executeUpdate();


            connection.commit();



        } catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();

            }
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_ACCOUNT_AND_CARD, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

        } finally {
            if(connection != null){
                connection.setAutoCommit(true);
                pool.closeConnection(connection, preparedStatementFrom, preparedStatementTo, resultSetFrom, resultSetTo);
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
                log.error(ErrorMessage.INCORRECT_SUM);
                throw new NumberFormatException(ErrorMessage.SUM_LESS_OR_EQUALS);
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
                log.error(ErrorMessage.INCORRECT_BALANCE);
                throw new SQLException(ErrorMessage.INCORRECT_BALANCE);
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
            transactionPreparedStatement.setString(5,STATUS);
            transactionPreparedStatement.setNull(6, Types.INTEGER);
            transactionPreparedStatement.setInt(7, 2);
            transactionPreparedStatement.setInt(8, 1);

            transactionPreparedStatement.executeUpdate();



            connection.commit();



        } catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();

            }
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_ACCOUNT_AND_CARD, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                pool.closeConnection(connection, preparedStatementFrom, preparedStatementTo, resultSetFrom, resultSetTo);
            }
        }
    }

    @Override
    public void transferFromCardToCard(String cardNumberFrom, String cardNumberTo, String expDate, double sum) throws DAOException, SQLException {
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
                log.error(ErrorMessage.INCORRECT_SUM);
                throw new NumberFormatException(ErrorMessage.SUM_LESS_OR_EQUALS);
            }

            preparedStatementFrom = connection.prepareStatement(SELECT_CARD_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(SELECT_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE);


            preparedStatementFrom.setString(1, cardNumberFrom);
            resultSetFrom = preparedStatementFrom.executeQuery();
            while (resultSetFrom.next()) {
                balanceOfCardFrom = resultSetFrom.getDouble(1);
            }

            double newBalanceOfCardFrom;
            if(balanceOfCardFrom >= sum){
                newBalanceOfCardFrom = balanceOfCardFrom - sum;
            } else {
                log.error(ErrorMessage.INCORRECT_BALANCE);
                throw new SQLException(ErrorMessage.INCORRECT_BALANCE);
            }

            preparedStatementTo.setString(1, cardNumberTo);
            preparedStatementTo.setString(2, expDate);
            resultSetTo = preparedStatementTo.executeQuery();
            while (resultSetTo.next()) {
                balanceOfAccountTo = resultSetTo.getDouble(1);
            }


            double newBalanceOfAccountTo = balanceOfAccountTo + sum;

            preparedStatementFrom = connection.prepareStatement(UPDATE_CARD_BALANCE_BY_NUMBER);
            preparedStatementTo = connection.prepareStatement(UPDATE_CARD_BALANCE_BY_NUMBER_AND_EXPIRE_DATE);


            preparedStatementFrom.setDouble(1, newBalanceOfCardFrom);
            preparedStatementFrom.setString(2, cardNumberFrom);
            preparedStatementFrom.executeUpdate();


            preparedStatementTo.setDouble(1, newBalanceOfAccountTo);
            preparedStatementTo.setString(2, cardNumberTo);
            preparedStatementTo.setString(3, expDate);
            preparedStatementTo.executeUpdate();


            //transaction table
            transactionPreparedStatement = connection.prepareStatement(INSERT_TRANSACTION);

            transactionPreparedStatement.setTimestamp(1, dateTime);
            transactionPreparedStatement.setString(2, cardNumberFrom);
            transactionPreparedStatement.setString(3,cardNumberTo);
            transactionPreparedStatement.setDouble(4,sum);
            transactionPreparedStatement.setString(5,STATUS);
            transactionPreparedStatement.setNull(6, Types.INTEGER);
            transactionPreparedStatement.setInt(7, 2);
            transactionPreparedStatement.setInt(8, 1);

            transactionPreparedStatement.executeUpdate();



            connection.commit();



        } catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();

            }
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_CARDS, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                pool.closeConnection(connection, preparedStatementFrom, preparedStatementTo, resultSetFrom, resultSetTo);
            }
        }
    }

    @Override
    public void topUpTheCard(String cardNumber, double sum) throws DAOException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        double balanceOfCard = 0;
        try {
            connection = pool.takeConnection();

            connection.setAutoCommit(false);

            if(sum <= 0){
                log.error(ErrorMessage.INCORRECT_SUM);
                throw new NumberFormatException(ErrorMessage.SUM_LESS_OR_EQUALS);
            }

            preparedStatement = connection.prepareStatement(SELECT_CARD_BALANCE_BY_NUMBER);
            preparedStatement.setString(1, cardNumber);


            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                balanceOfCard = resultSet.getDouble(1);
            }

            double newBalanceOfCard = balanceOfCard + sum;

            preparedStatement = connection.prepareStatement(UPDATE_CARD_BALANCE_BY_NUMBER);


            preparedStatement.setDouble(1, newBalanceOfCard);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();

            connection.commit();

        }catch (ConnectionPoolException | SQLException ex) {
            if (connection != null) {
                connection.rollback();

            }
            log.error(LoggerMessage.ERROR_DURING_TOPPING_UP_CARD, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

        }finally {
            if (connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

    }
}
