package com.tr.epay.service.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.dao.PaymentDAO;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.PaymentService;

import java.sql.SQLException;

public class SQLPaymentService implements PaymentService {
    DAOProvider provider = DAOProvider.getInstance();
    PaymentDAO paymentDAO = provider.getPaymentDAO();

    @Override
    public void transferFromAccountToAccount(String accFrom, String accTo, double sum) throws ServiceException {
        try{
            paymentDAO.transferFromAccountToAccount(accFrom,accTo,sum);

        }catch (DAOException | SQLException ex){
            throw new ServiceException("Transfer between accounts error", ex);
        }
    }

    @Override
    public void transferFromAccountToCard(String accNumber, String cardNumber, String expireDate, double sum) throws  ServiceException {
        try{
            paymentDAO.transferFromAccountToCard(accNumber, cardNumber, expireDate, sum);
        }catch (DAOException | SQLException ex){
            throw new ServiceException("Transfer between accounts and card", ex);
        }
    }

    @Override
    public void transferFromCardToAccount(String cardNumber, String accNumber, double sum) throws ServiceException {
        try{
            paymentDAO.transferFromCardToAccount(cardNumber, accNumber, sum);
        }catch (DAOException | SQLException ex){
            throw new ServiceException("Transfer between card and acc", ex);
        }
    }
}
