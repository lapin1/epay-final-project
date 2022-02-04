package com.tr.epay.service.impl.validator;

import java.util.HashMap;
import java.util.Map;

public class PaymentValidator {


    public Map<String, String> validateTransferToAccount(String accountNumber, double senderBalance, double sum){
        Map<String, String> errors = new HashMap<>();

        String regex = "\\d+";

        if(accountNumber.length() !=7 || !(accountNumber.matches(regex))){
            errors.put("Incorrect account number", "Incorrect account number, must contain 7 digits");
        }
        if(senderBalance < sum){
            errors.put("Low balance", "Insufficient funds");
        }

        if(sum <= 0){
            errors.put("Incorrect sum", "Sum less or equals zero");
        }

        return errors;
    }

    public Map<String, String> validateTransferToCard(String cardNumber, String expireDate, double senderBalance, double sum){
        Map<String, String> errors = new HashMap<>();

        String regex = "\\d+";
        String expirationDateRegex = "(?:0[1-9]|1[0-2])/2[3-9]";

        if(cardNumber.length() != 16 || !(cardNumber.matches(regex))){
            errors.put("card number error", "Incorrect card number, length must be 16 digits");
        }

        if(!expireDate.matches(expirationDateRegex)){
            errors.put("expiration date error","Incorrect expiration date, month must be 1-12, year must be > 22");
        }
        if(senderBalance < sum){
            errors.put("Low balance", "Insufficient funds");
        }

        if(sum <= 0){
            errors.put("Incorrect sum", "Sum less or equals zero");
        }

        return errors;
    }

    public Map<String, String> validateCardReplenishment(String cardNumber, double sum){
        Map<String, String> errors = new HashMap<>();

        String regex = "\\d+";

        if(cardNumber.length() != 16 || !(cardNumber.matches(regex))){
            errors.put("card number error", "Incorrect card number, length must be 16 digits");
        }

        if(sum <= 0){
            errors.put("Incorrect sum", "Sum less or equals zero");
        }

        return errors;
    }


}
