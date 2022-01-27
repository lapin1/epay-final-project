package com.tr.epay.service.impl.validator;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {


    public List<String> validate(String login, String password){
        List<String> errors = new ArrayList<>();


        if(login.length() == 0 || password.length() == 0){
            errors.add("one or more fields are empty");
        }




        return errors;
    }

}
