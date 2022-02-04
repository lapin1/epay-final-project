package com.tr.epay.service;

import com.tr.epay.entity.Card;


import java.util.List;

public interface CardService {
    List<Card> showCardsByAccountId(int id) throws ServiceException;
    void addCard(Card card, int accountId) throws ServiceException, ValidationException;
    Card selectCard(int id) throws ServiceException;
    boolean deleteCard(int id) throws ServiceException;
}
