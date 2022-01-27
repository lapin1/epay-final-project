package com.tr.epay.service;

import com.tr.epay.dao.DAOException;
import com.tr.epay.entity.Transaction;

public interface PaymentService {

    //с одного счета на другой
    void transferFromAccountToAccount(String accFrom, String accTo, double sum) throws DAOException, ServiceException;

    //вывод денег на карту
    void transferFromAccountToCard(String accNumber, String cardNumber,String expireDate, double sum) throws DAOException, ServiceException;

    void transferFromCardToAccount(String cardNumber, String accNumber, double sum) throws ServiceException;

}
