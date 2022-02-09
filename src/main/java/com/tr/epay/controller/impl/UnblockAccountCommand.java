package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.ParameterName;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnblockAccountCommand implements Command {
    private static final Logger log = Logger.getLogger(UnblockAccountCommand.class);
    ServiceProvider provider = ServiceProvider.getInstance();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        int blockedAccountID = Integer.parseInt(request.getParameter(ParameterName.BLOCKED_ACCOUNT_ID));


        try {
            AccountService accountService = provider.getAccountService();
            accountService.unblockAccount(blockedAccountID);
        }catch (ServiceException ex){

            log.error(ErrorMessage.SERVICE_LAYER_ERROR);
            response.sendRedirect(Path.GO_TO_HOME_PAGE);
        }

        response.sendRedirect(Path.GO_TO_HOME_PAGE);
    }
}
