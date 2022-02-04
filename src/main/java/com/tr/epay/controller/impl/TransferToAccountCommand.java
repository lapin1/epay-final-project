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

public class TransferToAccountCommand implements Command {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    PaymentService paymentService = serviceProvider.getTransactionService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String accountTo;
        double sum;
        String error = "Check data";
        HttpSession session = request.getSession();
        String accId = (String) session.getAttribute("selectedAccountNumber");
        accountTo = request.getParameter("accountNumberTo");
        sum = Double.parseDouble(request.getParameter("sum"));
        int accountId = (int) session.getAttribute("selectedAccountId");

        try{
            paymentService.transferFromAccountToAccount(accId,accountTo,sum);
        }catch (ValidationException ex){

            Map<String, String> errors = ex.getErrors();
            session.setAttribute("errorList", errors);
            response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId);
            return;
        }catch (ServiceException ex){

            response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId +"&message=" + error);
            return;
        }

        session.removeAttribute("errorList");
        String mess = "Transfer to account successful";
        response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId +"&message=" + mess);
    }
}
