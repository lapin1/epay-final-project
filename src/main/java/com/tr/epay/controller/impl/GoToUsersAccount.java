package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Account;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToUsersAccount implements Command {
    ServiceProvider provider = ServiceProvider.getInstance();
    AccountService accountService = provider.getAccountService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Account> accounts;
        int userId = Integer.parseInt(request.getParameter("id"));
        String errorMessage = "Something is wrong, please go back to main";
        RequestDispatcher dispatcher;

         try{
             accounts = accountService.showAccountsByUserId(userId);
             request.setAttribute("accounts", accounts);
         }catch (ServiceException ex){

             request.setAttribute("errorMessage", errorMessage);
             dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
             dispatcher.forward(request, response);
         }

         dispatcher= request.getRequestDispatcher("WEB-INF/jsp/userAccounts.jsp");
         dispatcher.forward(request, response);
    }
}
