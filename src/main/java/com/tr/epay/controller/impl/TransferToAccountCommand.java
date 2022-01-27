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

public class TransferToAccountCommand implements Command {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    PaymentService paymentService = serviceProvider.getTransactionService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {


        //String accountFrom;
        String accountTo;

        double sum;
        HttpSession session = request.getSession();

        //accountFrom = (String) session.getAttribute("selectedAccountNumber");
        String accId = (String) session.getAttribute("selectedAccountNumber");

        accountTo = request.getParameter("accountNumberTo");
        sum = Double.parseDouble(request.getParameter("sum"));


        paymentService.transferFromAccountToAccount(accId,accountTo,sum);
        System.out.println("SUCCESS");

        response.sendRedirect("MyController?command=GO_TO_HOME_PAGE");
    }
}
