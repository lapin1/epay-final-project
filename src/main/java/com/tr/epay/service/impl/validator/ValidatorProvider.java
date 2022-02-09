package com.tr.epay.service.impl.validator;

public class ValidatorProvider {
    private static final ValidatorProvider instance = new ValidatorProvider();

    private ValidatorProvider(){

    }

    private CardValidator cardValidator = new CardValidator();
    private UserValidator userValidator = new UserValidator();
    private PaymentValidator paymentValidator = new PaymentValidator();

    public static ValidatorProvider getInstance(){
        return instance;
    }

    public CardValidator getCardValidator() {
        return cardValidator;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public void setCardValidator(CardValidator cardValidator) {
        this.cardValidator = cardValidator;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    public PaymentValidator getPaymentValidator() {
        return paymentValidator;
    }

    public void setPaymentValidator(PaymentValidator paymentValidator) {
        this.paymentValidator = paymentValidator;
    }
}
