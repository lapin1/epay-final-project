package com.tr.epay.dao.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PayByCardTest {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SELECT_CARD_BALANCE_BALANCE_BY_ID = "SELECT balance FROM cards WHERE id = 1";
    //private static final String UPDATE_CARD_BALANCE_BY_NUMBER = "UPDATE cards SET balance = ? WHERE id = 1";
    private static final String SELECT_ACCOUNT_BALANCE_BY_NUMBER = "SELECT balance FROM accounts WHERE account_number = '4417244'";
    //private static final String UPDATE_ACCOUNT_BALANCE_BY_NUMBER = "UPDATE accounts SET balance = ? WHERE account_number = 4417244";


    public void transferFromCardToAccount(double sum) throws DAOException,SQLException {
        double cardBalance = 0;
        double accountBalance = 0;

        Connection connectionFrom = null;
        Connection connectionTo = null;
        Statement statementFrom = null;
        Statement statementTo = null;
        ResultSet resultSetFrom = null;
        ResultSet resultSetTo = null;

        try{


            connectionFrom = pool.takeConnection();
            connectionTo = pool.takeConnection();

            connectionFrom.setAutoCommit(false);
            connectionTo.setAutoCommit(false);

            statementFrom = connectionFrom.createStatement();
            statementTo = connectionTo.createStatement();

            resultSetFrom = statementFrom.executeQuery(SELECT_CARD_BALANCE_BALANCE_BY_ID);
            resultSetTo = statementTo.executeQuery(SELECT_ACCOUNT_BALANCE_BY_NUMBER);
           while (resultSetFrom.next()){
               cardBalance = resultSetFrom.getDouble(1);
           }
           cardBalance = cardBalance - sum;
           while (resultSetTo.next()){
               accountBalance = resultSetTo.getDouble(1);
           }
            accountBalance = accountBalance + sum;
            statementFrom.executeUpdate("UPDATE cards SET balance = "+ cardBalance + "  WHERE id = 1");
            statementTo.executeUpdate("UPDATE accounts SET balance = " + accountBalance + " WHERE account_number = '4417244'");


            connectionFrom.commit();
            connectionTo.commit();
            System.out.println("transaction success");
        } catch (ConnectionPoolException | SQLException e) {
            assert connectionFrom != null;
            connectionFrom.rollback();
            assert connectionTo != null;
            connectionTo.rollback();
            throw new DAOException("transaction error", e);
        }finally {
            assert connectionFrom != null;
            connectionFrom.close();
            assert connectionTo != null;
            connectionTo.close();
            assert statementFrom != null;
            statementFrom.close();
            assert statementTo != null;
            statementTo.close();
            assert resultSetFrom != null;
            resultSetFrom.close();
            assert resultSetTo != null;
            resultSetTo.close();

        }

    }

}
