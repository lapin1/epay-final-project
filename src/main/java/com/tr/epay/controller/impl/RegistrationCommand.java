package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.UserService;
import com.tr.epay.service.ValidationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class RegistrationCommand implements Command {
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private final UserService userService = serviceProvider.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = new User();
        UserInfo userInfo = new UserInfo();

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

        login = request.getParameter("login");
        password = request.getParameter("password");
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        city = request.getParameter("city");
        street = request.getParameter("street");
        state = request.getParameter("state");
        zip = request.getParameter("zip");
        phone = request.getParameter("phone");
        email = request.getParameter("email");

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

        try {
            userService.registration(user, userInfo);

            session = request.getSession();
            session.setAttribute("loginOfNewUser", login);

        }catch (ServiceException ex){

            response.sendRedirect("MyController?command=GO_TO_REGISTRATION_PAGE");
        }catch (ValidationException ex){
            Map<String, String> errors = ex.getErrors();
            session.setAttribute("errorRegList", errors);
            session.setAttribute("user", user);
            session.setAttribute("userInfo",userInfo);
            response.sendRedirect("MyController?command=GO_TO_REGISTRATION_PAGE");
        }

        session.removeAttribute("errorRegList");
        session.removeAttribute("user");
        session.removeAttribute("userInfo");
        response.sendRedirect("MyController?command=GO_TO_SUCCESS_PAGE");

    }
}
