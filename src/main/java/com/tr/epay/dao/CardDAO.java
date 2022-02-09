package com.tr.epay.dao;

import com.tr.epay.entity.Card;

import java.util.List;

public interface CardDAO {

    void addCard(Card card, int accountId)throws DAOException;
    boolean deleteCard(int id)throws DAOException;
    List<Card> showCardsByAccountId(int id) throws DAOException;
    Card selectCard(int id) throws DAOException;
    double getBalanceOfCard(String cardNumber) throws DAOException;
}

