package com.tr.epay.dao.impl;

import com.tr.epay.dao.CardDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.entity.Account;
import com.tr.epay.entity.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCardDAO implements CardDAO {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SHOW_CARDS_BY_ACCOUNT_ID = "select * from cards inner join accounts_has_cards on cards.id = cards_id inner join accounts on accounts.id = accounts_id where accounts_id = ?";
    private static final String SELECT_CARD_BY_ID ="select id, number, balance, cvv, exp_date from cards  where id = ?";
    private static final String ADD_CARD = "insert into cards (number, balance, cvv, exp_date) values (?,?,?,?)";
    private static final String ADD_KEYS = "insert into accounts_has_cards (accounts_id, cards_id) values (?,?)";

    @Override
    public void addCard(Card card, int accountId) throws DAOException {
        double balance = ((int)(10000 + (Math.random() * 100000)));
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementManyTable = null;

        int cardId = 0;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_CARD, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementManyTable = connection.prepareStatement(ADD_KEYS);

            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDouble(2, balance);
            preparedStatement.setInt(3, card.getCvv());
            preparedStatement.setString(4, card.getDate());


            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                cardId = resultSet.getInt(1);
            }

            preparedStatementManyTable.setInt(1,accountId);
            preparedStatementManyTable.setInt(2,cardId);

            preparedStatementManyTable.executeUpdate();


        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException("Error adding card", ex);
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
            if (preparedStatementManyTable != null) {
                try {
                    preparedStatementManyTable.close();
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
    public void deleteCard(int id) throws DAOException {

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
                card.setCvv(resultSet.getInt(4));
                card.setDate(resultSet.getString(5));

                cards.add(card);

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
                int cvv = rs.getInt(4);
                String date = rs.getString(5);
                card = new Card(id, number, balance, cvv, date);
            }


        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException("Error selecting card", ex);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
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
        return card;
    }
}
