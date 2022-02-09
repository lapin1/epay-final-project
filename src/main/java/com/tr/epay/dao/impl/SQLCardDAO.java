package com.tr.epay.dao.impl;

import com.tr.epay.dao.CardDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.entity.Card;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCardDAO implements CardDAO {
    private static final Logger log = Logger.getLogger(SQLCardDAO.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private static final String SHOW_CARDS_BY_ACCOUNT_ID = "SELECT * FROM cards INNER JOIN accounts_has_cards ON cards.id = cards_id INNER JOIN accounts ON accounts.id = accounts_id WHERE accounts_id = ?";
    private static final String SELECT_CARD_BY_ID ="SELECT id, number, balance, cvv, exp_date FROM cards  WHERE id = ?";
    private static final String ADD_CARD = "INSERT INTO cards (number, balance, cvv, exp_date) VALUES (?,?,?,?)";
    private static final String ADD_KEYS = "INSERT INTO accounts_has_cards (accounts_id, cards_id) VALUES (?,?)";
    private static final String DELETE_CARD = "DELETE FROM cards WHERE id = ?";
    private static final String DELETE_CARD_RELATIONSHIP = "DELETE FROM accounts_has_cards WHERE cards_id = ?";
    private static final String SELECT_CARD_BALANCE_BY_NUMBER = "SELECT balance FROM cards WHERE number = ?";



    @Override
    public void addCard(Card card, int accountId) throws DAOException {
        final double BALANCE = ((int)(10000 + (Math.random() * 100000)));
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementToManyTable = null;

        int cardId = 0;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_CARD, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementToManyTable = connection.prepareStatement(ADD_KEYS);
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDouble(2, BALANCE);
            preparedStatement.setString(3, card.getCvv());
            preparedStatement.setString(4, card.getDate());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()){
                cardId = resultSet.getInt(1);
            }

            preparedStatementToManyTable.setInt(1,accountId);
            preparedStatementToManyTable.setInt(2,cardId);
            preparedStatementToManyTable.executeUpdate();


        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_ADDING_CARD + accountId, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if (connection != null){
                pool.closeConnection(connection, preparedStatement, preparedStatementToManyTable, resultSet);
            }
        }

    }

    @Override
    public boolean deleteCard(int id) throws DAOException {
        boolean isDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatementRelationship = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = pool.takeConnection();
            preparedStatementRelationship = connection.prepareStatement(DELETE_CARD_RELATIONSHIP);
            preparedStatement = connection.prepareStatement(DELETE_CARD);


            preparedStatementRelationship.setInt(1, id);
            preparedStatement.setInt(1, id);

            if(preparedStatementRelationship.executeUpdate() > 0 && preparedStatement.executeUpdate() > 0){
                isDeleted = true;
            }
        }catch (ConnectionPoolException | SQLException ex){
            log.error(LoggerMessage.ERROR_DELETING_CARD + id, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatementRelationship, preparedStatement);
            }
        }
        return isDeleted;
    }

    @Override
    public List<Card> showCardsByAccountId(int id) throws DAOException {
        List<Card> cards = new ArrayList<>();
        Card card;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SHOW_CARDS_BY_ACCOUNT_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                card = new Card();
                card.setId(resultSet.getInt(1));
                card.setCardNumber(resultSet.getString(2));
                card.setBalance(resultSet.getDouble(3));
                card.setCvv(resultSet.getString(4));
                card.setDate(resultSet.getString(5));
                cards.add(card);

            }

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_SELECTING_CARD + id, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return cards;
    }

    @Override
    public Card selectCard(int cardId) throws DAOException {
        Card card = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CARD_BY_ID);
            preparedStatement.setInt(1, cardId);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                int id = rs.getInt(1);
                String number = rs.getString(2);
                double balance = rs.getDouble(3);
                String cvv = rs.getString(4);
                String date = rs.getString(5);
                card = new Card(id, number, balance, cvv, date);
            }

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_SELECTING_CARD + cardId, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, rs);
            }
        }
        return card;
    }

    @Override
    public double getBalanceOfCard(String cardNumber) throws DAOException {
        double cardBalance = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CARD_BALANCE_BY_NUMBER);
            preparedStatement.setString(1, cardNumber);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                cardBalance = resultSet.getDouble(1);

            }

        } catch (ConnectionPoolException | SQLException ex) {
            log.error(LoggerMessage.ERROR_SELECTING_CARD_BALANCE + cardNumber, ex);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return cardBalance;
    }
}
