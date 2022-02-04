package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.service.CardService;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteCardCommand implements Command {
    ServiceProvider provider = ServiceProvider.getInstance();
    CardService cardService = provider.getCardService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        int accountId = (int) session.getAttribute("selectedAccountId");

        try {
            cardService.deleteCard(id);
        }catch (ServiceException ex){

            String errorMessage = "Cannot delete card, try again";
            response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId +"&error=" + errorMessage);
        }

        String mess = "Card is deleted";
        response.sendRedirect("MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId +"&message=" + mess);

    }
}
