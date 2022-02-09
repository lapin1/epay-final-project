package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Account;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.ParameterName;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToUsersAccount implements Command {
    private static final Logger log = Logger.getLogger(GoToUsersAccount.class);
    private final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Account> accounts;
        int userId = Integer.parseInt(request.getParameter(ParameterName.ID));
        String errorMessage = "Something is wrong, please go back to main";
        RequestDispatcher dispatcher;

         try{
             AccountService accountService = provider.getAccountService();
             accounts = accountService.showAccountsByUserId(userId);
             request.setAttribute(AttributeName.ACCOUNTS, accounts);

         }catch (ServiceException ex){

             log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
             request.setAttribute(AttributeName.ERROR_MESSAGE, errorMessage);
             dispatcher = request.getRequestDispatcher(Path.ERROR_PAGE);
             dispatcher.forward(request, response);

         }

         dispatcher= request.getRequestDispatcher(Path.USER_ACCOUNTS);
         dispatcher.forward(request, response);
    }
}
