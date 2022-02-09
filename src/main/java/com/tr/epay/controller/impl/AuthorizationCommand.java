package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.*;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.ParameterName;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AuthorizationCommand implements Command {
    private static final Logger log = Logger.getLogger(AuthorizationCommand.class);
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login;
        String password;
        String errorMessage = "incorrect username or password";

        login = request.getParameter(ParameterName.LOGIN);
        password = request.getParameter(ParameterName.PASSWORD);

        try {
            UserService userService = serviceProvider.getUserService();
            boolean signInResult = userService.authorization(login, password);

            if (signInResult) {

                int id = userService.getIdOfAuthorizedUser(login);
                String userName = userService.getNameOfUser(id);
                String role = userService.getRoleOfUser(id);

                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.LOGIN_OF_USER, login);
                session.setAttribute(AttributeName.ID_OF_USER, id);
                session.setAttribute(AttributeName.ROLE_OF_USER, role);
                session.setAttribute(AttributeName.USER_NAME, userName);

                response.sendRedirect(Path.GO_TO_HOME_PAGE);
            } else {
                request.setAttribute(AttributeName.ERROR_MESSAGE, errorMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher(Path.AUTHORIZATION);
                dispatcher.forward(request, response);
            }

        } catch (ServiceException ex) {

            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Path.AUTHORIZATION);
            dispatcher.forward(request, response);

        } catch (ValidationException ex) {

            log.error(ErrorMessage.VALIDATION_ERROR, ex);
            Map<String, String> errors = ex.getErrors();
            request.setAttribute(AttributeName.ERROR_VALIDATOR, errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Path.AUTHORIZATION);
            dispatcher.forward(request, response);
        }
    }
}


