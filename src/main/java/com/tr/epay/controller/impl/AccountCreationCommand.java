package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Account;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccountCreationCommand implements Command {
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private final AccountService accountService = serviceProvider.getAccountService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        int id = (int)session.getAttribute("idOfUser"); //getting user id

        Account account = new Account();
        account.setUserId(id);

        try {
            accountService.createAccount(account);
        }catch (ServiceException ex){

            response.sendRedirect("MyController?command=GO_TO_HOME_PAGE");
        }

        response.sendRedirect("MyController?command=GO_TO_HOME_PAGE");
    }
}
