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

public class TransferToCardCommand implements Command {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    PaymentService paymentService = serviceProvider.getTransactionService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {


        String accountNumber;
        String cardNumber;
        String expireDate;
        double sum;

        HttpSession session = request.getSession();

        accountNumber = (String) session.getAttribute("selectedAccountNumber");

        cardNumber = request.getParameter("cardNumber");
        expireDate = request.getParameter("expireDate");
        sum = Double.parseDouble(request.getParameter("sum"));

        paymentService.transferFromAccountToCard(accountNumber, cardNumber, expireDate, sum);
        System.out.println("SUCCESS");

        response.sendRedirect("MyController?command=GO_TO_PAYMENT_PAGE");
    }
}
