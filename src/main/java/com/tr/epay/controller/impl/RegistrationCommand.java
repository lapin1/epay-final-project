package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.UserService;
import com.tr.epay.service.ValidationException;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.ParameterName;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class RegistrationCommand implements Command {
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = null;
        UserInfo userInfo = null;

        String login;
        String password;
        String firstName;
        String lastName;
        String city;
        String street;
        String state;
        String zip;
        String phone;
        String email;

        login = request.getParameter(ParameterName.LOGIN);
        password = request.getParameter(ParameterName.PASSWORD);
        firstName = request.getParameter(ParameterName.FIRST_NAME);
        lastName = request.getParameter(ParameterName.LAST_NAME);
        city = request.getParameter(ParameterName.CITY);
        street = request.getParameter(ParameterName.STREET);
        state = request.getParameter(ParameterName.STATE);
        zip = request.getParameter(ParameterName.ZIP);
        phone = request.getParameter(ParameterName.PHONE);
        email = request.getParameter(ParameterName.EMAIL);


        try {
             user = new User();
             userInfo = new UserInfo();

             user.setLogin(login);
             user.setPassword(password);
             userInfo.setFirstName(firstName);
             userInfo.setLastName(lastName);
             userInfo.setCity(city);
             userInfo.setStreet(street);
             userInfo.setState(state);
             userInfo.setZip(zip);
             userInfo.setPhone(phone);
             userInfo.setEmail(email);

            UserService userService = serviceProvider.getUserService();
            userService.registration(user, userInfo);

            session = request.getSession();
            session.setAttribute(AttributeName.LOGIN_OF_USER, login);

        }catch (ServiceException ex){

            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            response.sendRedirect(Path.GO_TO_REGISTRATION_PAGE);
            System.out.println("serv err");

        }catch (ValidationException ex){
            System.out.println("val er");
            log.error(ErrorMessage.VALIDATION_ERROR, ex);
            Map<String, String> errors = ex.getErrors();
            session.setAttribute(AttributeName.ERRORS_DURING_REGISTRATION, errors);
            session.setAttribute(AttributeName.USER, user);
            session.setAttribute(AttributeName.USER_INFO,userInfo);
            response.sendRedirect(Path.GO_TO_REGISTRATION_PAGE);
            return;
        }

        session.removeAttribute(AttributeName.ERRORS_DURING_REGISTRATION);
        session.removeAttribute(AttributeName.USER);
        session.removeAttribute(AttributeName.USER_INFO);
        response.sendRedirect(Path.GO_TO_REGISTRATION_SUCCESS_PAGE);

    }
}
