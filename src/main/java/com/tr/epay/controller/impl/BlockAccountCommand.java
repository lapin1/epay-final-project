package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.AccountService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BlockAccountCommand implements Command {
    private static final Logger log = Logger.getLogger(BlockAccountCommand.class);
    private final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = (int)session.getAttribute(AttributeName.SELECTED_ACCOUNT_ID);

        try {
            AccountService accountService = provider.getAccountService();
            accountService.blockAccount(id);
        }catch (ServiceException ex){
            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            response.sendRedirect(Path.GO_TO_HOME_PAGE);
        }

        response.sendRedirect(Path.GO_TO_HOME_PAGE);

    }
}
