package com.tr.epay.service.impl;

import com.tr.epay.dao.CardDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.entity.Card;
import com.tr.epay.service.CardService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ValidationException;
import com.tr.epay.service.impl.validator.CardValidator;
import com.tr.epay.service.impl.validator.ValidatorProvider;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import jdk.internal.net.http.common.Log;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class SQLCardService implements CardService {
    private static final Logger log = Logger.getLogger(SQLCardService.class);
    DAOProvider provider = DAOProvider.getInstance();
    CardValidator cardValidator = ValidatorProvider.getInstance().getCardValidator();
    CardDAO cardDAO = provider.getCardDAO();

    @Override
    public List<Card> showCardsByAccountId(int id) throws ServiceException {
        List<Card> cards;

        try {
            cards = cardDAO.showCardsByAccountId(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_CARD + id);
            throw new ServiceException(LoggerMessage.ERROR_SELECTING_CARD, ex);
        }

        return cards;
    }

    @Override
    public void addCard(Card card, int accountId) throws ServiceException, ValidationException {
        Map<String, String> errors = cardValidator.validate(card);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try {
            cardDAO.addCard(card,accountId);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_ADDING_CARD);
            throw new ServiceException(LoggerMessage.ERROR_ADDING_CARD, ex);
        }
    }


    @Override
    public Card selectCard(int id) throws ServiceException {
        Card card;
        try {
            card = cardDAO.selectCard(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_CARD + id);
            throw new ServiceException(LoggerMessage.ERROR_SELECTING_CARD, ex);
        }
        return card;
    }

    @Override
    public boolean deleteCard(int id) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = cardDAO.deleteCard(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_DELETING_CARD + id);
            throw new ServiceException(LoggerMessage.ERROR_DELETING_CARD, ex);
        }
        return isDeleted;
    }


}
