package com.tr.epay.service.impl;

import com.tr.epay.dao.*;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.PaymentService;
import com.tr.epay.service.ValidationException;
import com.tr.epay.service.impl.validator.PaymentValidator;
import com.tr.epay.service.impl.validator.ValidatorProvider;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.Map;

public class SQLPaymentService implements PaymentService {
    private static final Logger log = Logger.getLogger(SQLPaymentService.class);
    private final DAOProvider provider = DAOProvider.getInstance();


    @Override
    public void transferFromAccountToAccount(String accFrom, String accTo, double sum) throws ServiceException, ValidationException{
        double balanceOfSender;

        try {
            AccountDAO accountDAO = provider.getAccountDAO();
            balanceOfSender = accountDAO.getBalanceOfAccount(accFrom);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_BALANCE + accFrom, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
        PaymentValidator paymentValidator = ValidatorProvider.getInstance().getPaymentValidator();
        Map<String, String> errors = paymentValidator.validateTransferToAccount(accTo, balanceOfSender, sum);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR + accFrom + " " + accTo);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try{
            PaymentDAO paymentDAO = provider.getPaymentDAO();
            paymentDAO.transferFromAccountToAccount(accFrom,accTo,sum);

        }catch (DAOException | SQLException ex){
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_ACCOUNTS, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
    }

    @Override
    public void transferFromAccountToCard(String accNumber, String cardNumber, String expireDate, double sum) throws ServiceException, ValidationException {
        double balanceOfSender;

        try {
            AccountDAO accountDAO = provider.getAccountDAO();
            balanceOfSender = accountDAO.getBalanceOfAccount(accNumber);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_BALANCE + accNumber, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        PaymentValidator paymentValidator = ValidatorProvider.getInstance().getPaymentValidator();
        Map<String, String> errors = paymentValidator.validateTransferToCard(cardNumber, expireDate, balanceOfSender, sum);

        if(!errors.isEmpty()){
           log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try{
            PaymentDAO paymentDAO = provider.getPaymentDAO();
            paymentDAO.transferFromAccountToCard(accNumber, cardNumber, expireDate, sum);

        }catch (DAOException | SQLException ex){
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_ACCOUNT_AND_CARD, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
    }

    @Override
    public void transferFromCardToAccount(String cardNumber, String accNumber, double sum) throws ServiceException, ValidationException {
        double balanceOfSender;

        try {
            CardDAO cardDAO = provider.getCardDAO();
            balanceOfSender = cardDAO.getBalanceOfCard(cardNumber);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_BALANCE, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        PaymentValidator paymentValidator = ValidatorProvider.getInstance().getPaymentValidator();
        Map<String, String> errors = paymentValidator.validateTransferToAccount(accNumber, balanceOfSender, sum);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try{
            PaymentDAO paymentDAO = provider.getPaymentDAO();
            paymentDAO.transferFromCardToAccount(cardNumber, accNumber, sum);
        }catch (DAOException | SQLException ex){
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_ACCOUNT_AND_CARD, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
    }

    @Override
    public void transferFromCardToCard(String cardNumberFrom, String cardNumberTo, String date, double sum) throws ServiceException, ValidationException {
        double balanceOfSender;

        try {
            CardDAO cardDAO = provider.getCardDAO();
            balanceOfSender = cardDAO.getBalanceOfCard(cardNumberFrom);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_BALANCE, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        PaymentValidator paymentValidator = ValidatorProvider.getInstance().getPaymentValidator();
        Map<String, String> errors = paymentValidator.validateTransferToCard(cardNumberTo, date, balanceOfSender, sum);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try{
            PaymentDAO paymentDAO = provider.getPaymentDAO();
            paymentDAO.transferFromCardToCard(cardNumberFrom, cardNumberTo, date, sum);
        }catch (DAOException | SQLException ex){
            log.error(LoggerMessage.ERROR_DURING_TRANSACTION_BETWEEN_CARDS, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
    }

    @Override
    public void topUpTheCard(String cardNumber, double sum) throws ServiceException, ValidationException {
        PaymentValidator paymentValidator = ValidatorProvider.getInstance().getPaymentValidator();
        Map<String, String> errors = paymentValidator.validateCardReplenishment(cardNumber, sum);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try{
            PaymentDAO paymentDAO = provider.getPaymentDAO();
            paymentDAO.topUpTheCard(cardNumber, sum);
        }catch (DAOException | SQLException ex){
           log.error(LoggerMessage.ERROR_DURING_TOPPING_UP_CARD, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
    }
}
