package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.PaymentService;
import com.tr.epay.service.ValidationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class TransferByCardToAccount implements Command {
    ServiceProvider provider = ServiceProvider.getInstance();
    PaymentService paymentService = provider.getTransactionService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String mess = "Transfer by card to account successful";
        String cardFrom;
        String accountTo;
        double sum;

        cardFrom = request.getParameter("selectedCard");
        accountTo = request.getParameter("accountNumberTo");
        sum = Double.parseDouble(request.getParameter("sum"));
        int accountId = (int) session.getAttribute("selectedAccountId");

        try{
            paymentService.transferFromCardToAccount(cardFrom, accountTo, sum);
        }catch (ServiceException ex){
            response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId);
            return;
        }catch (ValidationException ex){
            Map<String, String> errors = ex.getErrors();
            session.setAttribute("errorList", errors);
            response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId);
        }

        session.removeAttribute("errorList");
        response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId +"&message=" + mess);

    }
}
