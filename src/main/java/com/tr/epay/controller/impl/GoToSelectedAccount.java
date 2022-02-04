package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Account;
import com.tr.epay.entity.Card;
import com.tr.epay.entity.Transaction;
import com.tr.epay.service.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GoToSelectedAccount implements Command {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    AccountService accountService = serviceProvider.getAccountService();
    CardService cardService = serviceProvider.getCardService();
    TransactionService transactionService = serviceProvider.getListTransactionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.getAttribute("loginOfUser");
        Account account = null;
        String num = null;
        List<Card> cards = null;
        List<Transaction> accTransactions = null;
        String errorMessage = "Something error, please go back to main";
        int accountId = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher dispatcher;
        session.setAttribute("curAcc", accountId);

        try{
            account = accountService.selectAccount(accountId);
            num = account.getAccountNumber();
            session.setAttribute("selectedAccountNumber",account.getAccountNumber());
            session.setAttribute("selectedAccountBalance",account.getBalance());
            session.setAttribute("selectedAccountId", account.getId());
        }catch (ServiceException ex){

            request.setAttribute("errorMessage", errorMessage);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }

        request.setAttribute("selectedAccount", account);

         try{
             cards = cardService.showCardsByAccountId(accountId);
             accTransactions = transactionService.showAccountTransactionsById(num);
         }catch (ServiceException ex){

             request.setAttribute("errorMessage", errorMessage);
             dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
             dispatcher.forward(request, response);
         }

        session.setAttribute("acctr", accTransactions);
        session.setAttribute("cards", cards);
        session.setAttribute("url", "MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId);

        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/account.jsp");
        dispatcher.forward(request, response);
    }
}
