package com.tr.epay.service.impl;

import com.tr.epay.dao.CardDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.entity.Card;
import com.tr.epay.service.CardService;
import com.tr.epay.service.ServiceException;

import java.util.List;

public class SQLCardService implements CardService {
    DAOProvider provider = DAOProvider.getInstance();
    CardDAO cardDAO = provider.getCardDAO();
    @Override
    public List<Card> showCardsByAccountId(int id) throws DAOException, ServiceException {
        List<Card> cards;

        try {
            cards = cardDAO.showCardsByAccountId(id);
        }catch (DAOException ex){
            throw new ServiceException("Error show cards", ex);
        }

        return cards;
    }

    @Override
    public void addCard(Card card, int accountId) throws ServiceException {
        try {
            cardDAO.addCard(card,accountId);
        }catch (DAOException ex){
            throw new ServiceException("Error adding card", ex);
        }
    }


    @Override
    public Card selectCard(int id) throws ServiceException {
        Card card;
        try {
            card = cardDAO.selectCard(id);
        }catch (DAOException ex){
            throw new ServiceException("Error selecting card", ex);
        }
        return card;
    }
}
