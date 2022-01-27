package com.tr.epay.service;

import com.tr.epay.dao.DAOException;
import com.tr.epay.entity.Card;


import java.util.List;

public interface CardService {
    List<Card> showCardsByAccountId(int id) throws DAOException, ServiceException;
    void addCard(Card card, int accountId) throws DAOException, ServiceException;
    Card selectCard(int id) throws ServiceException;
}
