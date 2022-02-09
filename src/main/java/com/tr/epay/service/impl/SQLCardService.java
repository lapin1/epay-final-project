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
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Map;

public class SQLCardService implements CardService {
    private static final Logger log = Logger.getLogger(SQLCardService.class);
    private final DAOProvider provider = DAOProvider.getInstance();

    @Override
    public List<Card> showCardsByAccountId(int id) throws ServiceException {
        List<Card> cards;

        try {
            CardDAO cardDAO = provider.getCardDAO();
            cards = cardDAO.showCardsByAccountId(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_CARD + id, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        return cards;
    }

    @Override
    public void addCard(Card card, int accountId) throws ServiceException, ValidationException {

        CardValidator cardValidator = ValidatorProvider.getInstance().getCardValidator();
        Map<String, String> errors = cardValidator.validate(card);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try {
            CardDAO cardDAO = provider.getCardDAO();
            cardDAO.addCard(card,accountId);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_ADDING_CARD, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
    }


    @Override
    public Card selectCard(int id) throws ServiceException {
        Card card;
        try {
            CardDAO cardDAO = provider.getCardDAO();
            card = cardDAO.selectCard(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_CARD + id, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
        return card;
    }

    @Override
    public boolean deleteCard(int id) throws ServiceException {
        boolean isDeleted;
        try {
            CardDAO cardDAO = provider.getCardDAO();
            isDeleted = cardDAO.deleteCard(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_DELETING_CARD + id, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
        return isDeleted;
    }


}
