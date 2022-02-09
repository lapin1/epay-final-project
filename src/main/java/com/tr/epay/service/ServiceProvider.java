package com.tr.epay.service;


import com.tr.epay.service.impl.*;

public final class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private ServiceProvider() {

    }

    private UserService userService = new SQLUserService();
    private AccountService accountService = new SQLAccountService();
    private CardService cardService = new SQLCardService();
    private PaymentService paymentService = new SQLPaymentService();
    private TransactionService listTransactionService = new SQLTransactionService();

    public TransactionService getListTransactionService() {
        return listTransactionService;
    }

    public void setListTransactionService(TransactionService listTransactionService) {
        this.listTransactionService = listTransactionService;
    }

    public static ServiceProvider getInstance(){
        return instance;
    }

    public UserService getUserService(){
        return userService;
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}