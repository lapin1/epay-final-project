package com.tr.epay.service.impl.validator;

import com.tr.epay.entity.Card;

import java.util.HashMap;
import java.util.Map;

public class CardValidator {

    public Map<String, String> validate(Card card){
        String cardNumber = card.getCardNumber();
        String expDate = card.getDate();
        String cvv = card.getCvv();

        String regex = "\\d+";
        String expirationDateRegex = "(?:0[1-9]|1[0-2])/2[3-9]";


        Map<String, String> errors = new HashMap<>();



        if(cardNumber.length() != 16 || !(cardNumber.matches(regex))){
            errors.put("card number error", "Incorrect card number, length must be 16 digits");
        }

        if(!expDate.matches(expirationDateRegex)){
            errors.put("expiration date error","Incorrect expiration date, month must be 1-12, year must be > 22");
        }

        if(cvv.length() != 3 || !(cvv.matches(regex))){
            errors.put("cvv error", "Incorrect cvv, cvv must be 3 digits");
        }

        return errors;
    }


}
