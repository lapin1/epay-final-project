package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;

import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TopUpAccountCommand implements Command {
    ServiceProvider provider = ServiceProvider.getInstance();
    PaymentService transactionService = provider.getTransactionService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {
        HttpSession session = request.getSession();

        String accountNumber = (String) session.getAttribute("selectedAccountNumber");
        String cardNumber = request.getParameter("selectedCard");
        double sum = Double.parseDouble(request.getParameter("sum"));

        transactionService.transferFromCardToAccount(cardNumber, accountNumber, sum);

        response.sendRedirect("MyController?command=GO_TO_HOME_PAGE");




    }
}
