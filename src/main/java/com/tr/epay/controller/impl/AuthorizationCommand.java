package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AuthorizationCommand implements Command {
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private final UserService userService = serviceProvider.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login;
        String password;

        login = request.getParameter("login");
        password = request.getParameter("password");

        try {
            boolean signInResult = userService.authorization(login, password);

            if (signInResult) {

                int id = userService.getIdOfAuthorizedUser(login);
                String userName = userService.getNameOfUser(id);
                String role = userService.getRoleOfUser(id);

                HttpSession session = request.getSession();
                session.setAttribute("loginOfUser", login);
                session.setAttribute("idOfUser", id);
                session.setAttribute("role", role);
                session.setAttribute("userName", userName);

                response.sendRedirect("MyController?command=GO_TO_HOME_PAGE");
            } else {
                request.setAttribute("errorMessage", "incorrect username or password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp");
                dispatcher.forward(request, response);
            }

        } catch (ServiceException ex) {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp");
            dispatcher.forward(request, response);
        } catch (ValidationException ex) {

            Map<String, String> errors = ex.getErrors();
            request.setAttribute("errorValidator", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp");
            dispatcher.forward(request, response);
        }
    }
}


