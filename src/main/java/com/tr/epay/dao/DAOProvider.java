package com.tr.epay.dao;

import com.tr.epay.dao.impl.*;

public final class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();

    private DAOProvider() {

    }

    private UserDAO userDAO = new SQLUserDAO();
    private AccountDAO accountDAO = new SQLAccountDAO();
    private CardDAO cardDAO = new SQLCardDAO();
    private PaymentDAO paymentDAO = new SQLPaymentDAO();
    private TransactionDAO transactionDAO = new SQLTransactionDAO();

    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }

    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public static DAOProvider getInstance(){
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public CardDAO getCardDAO() {
        return cardDAO;
    }

    public void setCardDAO(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }

    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

   // public TransactionDAO getTransactionDAO() {
        //return transactionDAO;
    //}

    //public void setTransactionDAO(TransactionDAO transactionDAO) {
    //    this.transactionDAO = transactionDAO;
    //}
}
