package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Card;
import com.tr.epay.service.CardService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.ValidationException;
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
import java.util.Map;

public class AddCardCommand implements Command {
    private static final Logger log = Logger.getLogger(AddCardCommand.class);
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute(AttributeName.SELECTED_ACCOUNT_ID);

        String number = request.getParameter(ParameterName.CARD_NUMBER);
        String month = request.getParameter(ParameterName.MONTH);
        String year = request.getParameter(ParameterName.YEAR);
        String expirationDate = month + "/" + year;
        String cvv = request.getParameter(ParameterName.CVV);

        try{
            CardService cardService = serviceProvider.getCardService();
            Card card = new Card();
            card.setCardNumber(number);
            card.setDate(expirationDate);
            card.setCvv(cvv);
            cardService.addCard(card,id);
        }
        catch (ValidationException ex){

            log.error(ErrorMessage.VALIDATION_ERROR, ex);
            Map<String,String> errors = ex.getErrors();
            session.setAttribute(AttributeName.ERROR_LIST, errors);
            response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + id);
            return;

        }catch (ServiceException ex){

            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + id);
        }

        session.removeAttribute(AttributeName.ERROR_LIST);
        String messageSuccess = "Card added";
        response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + id + "&message=" + messageSuccess);

    }
}
