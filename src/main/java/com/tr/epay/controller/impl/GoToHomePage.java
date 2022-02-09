package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Account;
import com.tr.epay.entity.User;
import com.tr.epay.service.*;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GoToHomePage implements Command {
    private static final Logger log = Logger.getLogger(GoToHomePage.class);
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.setAttribute(AttributeName.URL, Path.GO_TO_HOME_PAGE);

        int id = (int) session.getAttribute(AttributeName.ID_OF_USER);
        String role = (String) session.getAttribute(AttributeName.ROLE_OF_USER);
        String login = (String) session.getAttribute(AttributeName.LOGIN_OF_USER);
        String roleNameAdmin = "administrator";
        String roleNameUser = "client";
        String errorMessage = "Something is wrong, please go back to main";
        List<Account> accounts = null;
        List<User> users = null;


        RequestDispatcher dispatcher;


        if (!(login == null) && role.equals(roleNameUser)) {

            try {
                AccountService accountService = serviceProvider.getAccountService();
                accounts = accountService.showAccountsByUserId(id);

            } catch (ServiceException ex) {

                log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
                request.setAttribute(AttributeName.ERROR_MESSAGE, errorMessage);
                dispatcher = request.getRequestDispatcher(Path.ERROR_PAGE);
                dispatcher.forward(request, response);
            }

            request.setAttribute(AttributeName.ID_OF_USER, id);   //forInfo
            request.setAttribute(AttributeName.ACCOUNTS, accounts);

            dispatcher = request.getRequestDispatcher(Path.USER_HOME);
            dispatcher.forward(request, response);

        } else if (!(login == null) && role.equals(roleNameAdmin)) {

            {
                try {
                    UserService userService = serviceProvider.getUserService();
                    users = userService.viewAllUsers();
                } catch (ServiceException ex) {

                    request.setAttribute(AttributeName.ERROR_MESSAGE, errorMessage);
                    dispatcher = request.getRequestDispatcher(Path.ERROR_PAGE);
                    dispatcher.forward(request, response);
                }
                request.setAttribute(AttributeName.USERS, users);
                dispatcher = request.getRequestDispatcher(Path.ADMIN_HOME);
                dispatcher.forward(request, response);
            }
        } else {
            log.error(ErrorMessage.USER_NOT_AUTHORIZED);
            response.sendRedirect(Path.GO_TO_AUTHORIZATION_PAGE);
        }


    }
}
