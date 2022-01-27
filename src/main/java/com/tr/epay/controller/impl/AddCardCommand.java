package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
import com.tr.epay.entity.Card;
import com.tr.epay.service.CardService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddCardCommand implements Command {
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private final CardService cardService = serviceProvider.getCardService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("selectedAccountId");

        String number = request.getParameter("cardNumber");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String expirationDate = month + "-" + year;
        int cvv = Integer.parseInt(request.getParameter("cvv"));

        try{
            Card card = new Card();
            card.setCardNumber(number);
            card.setDate(expirationDate);
            card.setCvv(cvv);

            cardService.addCard(card,id);
        }catch (ServiceException ex){
            request.setAttribute("errorMessage", "something wrong, try again"); //
            response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + id);
        }catch (ValidationException ex){
            ex.getErrors();
        }


        response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + id);


    }
}
