package com.tr.epay.service.impl.validator;

import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {

    public Map<String, String> validateSignIn(String login, String password){
        Map<String,String> errors = new HashMap<>();


        if(login.length() < 3 || password.length() < 8){
            errors.put("Incorrect input", "Check input data");
        }


        return errors;
    }



    public Map<String, String> validateSignUp(User user, UserInfo userInfo){
        String login = user.getLogin();
        String password = user.getPassword();
        String firstName = userInfo.getFirstName();
        String lastName = userInfo.getLastName();
        String city = userInfo.getCity();
        String street = userInfo.getStreet();
        String state = userInfo.getState();
        String zip = userInfo.getZip();
        String phone = userInfo.getPhone();
        String email = userInfo.getEmail();

        String regexLogin = "^[a-zA-Z0-9._-]{4,}$";
        String regexPassword = "^[a-zA-Z0-9._-]{8,}$";
        String regexMain = "^[a-zA-Z]{3,}$";
        String regexDigits = "\\d+";
        String regexEmail = "^[a-zA-Z0-9]+[@][a-z]{4,}[.][a-z]{2,}";


        Map<String,String> errors = new HashMap<>();
        if(!(login.matches(regexLogin))){
            errors.put("loginError", "login length must be > 3, latin characters, can be with .,-,_");
        }
        if(!(password.matches(regexPassword))){
            errors.put("passwordError", "password length must be > 7, latin characters, can be with .,-,_");
        }
        if(!(firstName.matches(regexMain))){
            errors.put("firstNameError", "incorrect first name input");
        }
        if(!(lastName.matches(regexMain))){
            errors.put("lastNameError", "incorrect last name input");
        }
        if(!(city.matches(regexMain))){
            errors.put("cityError", "incorrect city input");
        }
        if(!(state.matches(regexMain))){
            errors.put("stateError", "incorrect state input");
        }
        if(!(street.matches(regexMain))){
            errors.put("streetError", "incorrect street input");
        }

        if(!(zip.length() < 3) || !(zip.matches(regexDigits)) ){
            errors.put("zipError", "incorrect zip input");
        }
        if(!(phone.length() < 6) || !(phone.matches(regexDigits)) ){
            errors.put("phoneError", "incorrect phone input");
        }
        if(!(email.length() < 2) || !(email.matches(regexEmail))){
            errors.put("emailError", "incorrect email input");
        }

        return errors;
    }

}
