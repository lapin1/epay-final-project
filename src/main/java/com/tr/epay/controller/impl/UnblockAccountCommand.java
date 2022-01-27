package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnblockAccountCommand implements Command {
    ServiceProvider provider = ServiceProvider.getInstance();
    AccountService accountService = provider.getAccountService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        accountService.unblockAccount(accountId);



        response.sendRedirect("MyController?command=GO_TO_HOME_PAGE");
    }
}
