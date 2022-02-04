package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Account;
import com.tr.epay.entity.User;
import com.tr.epay.service.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GoToHomePage implements Command {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    AccountService accountService = serviceProvider.getAccountService();
    UserService userService = serviceProvider.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.setAttribute("url", "MyController?command=GO_TO_HOME_PAGE");

        int id = (int)session.getAttribute("idOfUser");
        String role = (String) session.getAttribute("role");
        List<Account> accounts = null;
        List<User> users = null;
        String errorMessage = "Something is wrong, please go back to main";

        RequestDispatcher dispatcher;

        try {
            accounts = accountService.showAccountsByUserId(id);
        }catch (ServiceException ex){

            request.setAttribute("errorMessage", errorMessage);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }

        request.setAttribute("accounts", accounts);

        if(!role.equals("administrator")) {
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userHome.jsp");
        }else {
            try {
                users = userService.viewAllUsers();
            }catch (ServiceException ex){

                request.setAttribute("errorMessage", errorMessage);
                dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
                dispatcher.forward(request, response);
            }
            request.setAttribute("users", users);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminHome.jsp");
        }
        dispatcher.forward(request, response);
    }
}
