package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
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

public class GoToHomePageCommand implements Command {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    AccountService accountService = serviceProvider.getAccountService();
    UserService userService = serviceProvider.getUserService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {

        HttpSession session = request.getSession();



        int id = (int)session.getAttribute("idOfUser");
        String userName = (String) session.getAttribute("userName");
        String role = (String) session.getAttribute("role") ;


        List<Account> accounts = accountService.showAccountsByUserId(id);
        request.setAttribute("accounts", accounts);

        session.setAttribute("url", "MyController?command=GO_TO_HOME_PAGE");
        RequestDispatcher dispatcher;

        if(!role.equals("administrator")) {


            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userHome.jsp");
        }else {
            List<User> users = userService.viewAllUsers();
            request.setAttribute("users", users);

            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminHome.jsp");
        }

        dispatcher.forward(request, response);
    }
}
