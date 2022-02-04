package com.tr.epay.dao;

import java.sql.SQLException;

public interface PaymentDAO {

    void transferFromAccountToAccount(String accFrom, String accTo, double sum) throws DAOException, SQLException;
    void transferFromAccountToCard(String accNumber, String cardNumber,String expireDate, double sum) throws DAOException, SQLException;
    void transferFromCardToAccount(String cardNumber, String accNumber, double sum) throws DAOException, SQLException;
    void transferFromCardToCard(String cardNumberFrom, String cardNumberTo, String date, double sum) throws DAOException, SQLException;
    //ATM test
    void topUpTheCard(String cardNumber, double sum) throws DAOException, SQLException;
}
