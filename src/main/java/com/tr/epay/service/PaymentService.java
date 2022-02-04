package com.tr.epay.service;

public interface PaymentService {

    void transferFromAccountToAccount(String accFrom, String accTo, double sum) throws ServiceException, ValidationException;
    void transferFromAccountToCard(String accNumber, String cardNumber,String expireDate, double sum) throws ServiceException, ValidationException;
    void transferFromCardToAccount(String cardNumber, String accNumber, double sum) throws ServiceException, ValidationException;
    void transferFromCardToCard(String cardNumberFrom, String cardNumberTo, String date, double sum) throws ServiceException, ValidationException;
    void topUpTheCard(String cardNumber, double sum) throws ServiceException, ValidationException;

}
