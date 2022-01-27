package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransferByCardToAccount implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {
        ServiceProvider provider = ServiceProvider.getInstance();
        PaymentService paymentService = provider.getTransactionService();


        String cardFrom;
        String accountTo;
        double sum;

        cardFrom = request.getParameter("selectedCard");


        accountTo = request.getParameter("accountNumberTo");
        sum = Double.parseDouble(request.getParameter("sum"));

        paymentService.transferFromCardToAccount(cardFrom, accountTo, sum);

        System.out.println(cardFrom);
        System.out.println(accountTo);
        System.out.println(sum);



        response.sendRedirect("MyController?command=GO_TO_HOME_PAGE");


    }
}
