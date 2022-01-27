package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {
        ServiceProvider provider = ServiceProvider.getInstance();
        AccountService accountService = provider.getAccountService();

        int userId = Integer.parseInt(request.getParameter("id"));

       List<Account> accounts = accountService.showAccountsByUserId(userId);

        System.out.println(accounts);
       request.setAttribute("accounts", accounts);


        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userAccounts.jsp");
        dispatcher.forward(request, response);
    }
}
