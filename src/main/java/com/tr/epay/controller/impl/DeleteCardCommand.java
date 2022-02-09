package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.CardService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.ParameterName;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteCardCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteCardCommand.class);
    private final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter(ParameterName.ID));
        int accountId = (int) session.getAttribute(AttributeName.SELECTED_ACCOUNT_ID);

        try {
            CardService cardService = provider.getCardService();
            cardService.deleteCard(id);
        }catch (ServiceException ex){

            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            String errorMessage = "Cannot delete card, try again";
            response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + accountId +"&error=" + errorMessage);
        }

        String mess = "Card is deleted";
        response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + accountId +"&message=" + mess);

    }
}
