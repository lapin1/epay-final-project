package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.PaymentService;
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

public class TransferByCardToAccount implements Command {
    private static final Logger log = Logger.getLogger(TransferByCardToAccount.class);
    private final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String mess = "Transfer by card to account successful";
        String cardFrom;
        String accountTo;
        double sum;

        cardFrom = request.getParameter(ParameterName.CARD_NUMBER_FROM);
        accountTo = request.getParameter(ParameterName.ACCOUNT_NUMBER_TO);
        sum = Double.parseDouble(request.getParameter(ParameterName.SUM));
        int accountId = (int) session.getAttribute(AttributeName.SELECTED_ACCOUNT_ID);

        try{
            PaymentService paymentService = provider.getPaymentService();
            paymentService.transferFromCardToAccount(cardFrom, accountTo, sum);
        }catch (ServiceException ex){

            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + accountId);
            return;

        }catch (ValidationException ex){

            log.error(ErrorMessage.VALIDATION_ERROR, ex);
            Map<String, String> errors = ex.getErrors();
            session.setAttribute(AttributeName.ERROR_LIST, errors);
            response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + accountId);
            return;
        }

        session.removeAttribute(AttributeName.ERROR_LIST);
        response.sendRedirect(Path.GO_TO_SELECTED_ACCOUNT + accountId +"&message=" + mess);

    }
}
