package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {


        HttpSession session = request.getSession();
        session.getAttribute("loginOfUser");

        int accountId = Integer.parseInt(request.getParameter("id"));
        System.out.println(accountId);
        RequestDispatcher dispatcher;

        session.setAttribute("curAcc", accountId);


        Account account = accountService.selectAccount(accountId);
        request.setAttribute("selectedAccount", account);

        session.setAttribute("selectedAccountNumber",account.getAccountNumber());
        session.setAttribute("selectedAccountBalance",account.getBalance());
        session.setAttribute("selectedAccountId", account.getId());
        //System.out.println(account);


        List<Card> cards = cardService.showCardsByAccountId(accountId);
        List<Transaction> accTransactions = transactionService.showAccountTransactionsById(accountId);
        //List<Transaction> cardTransactions = transactionService.showCardTransactionsById();

        System.out.println("acccccccc" + accountId);

        session.setAttribute("acctr", accTransactions);
        session.setAttribute("cards", cards);


        session.setAttribute("url", "MyController?command=GO_TO_SELECTED_ACCOUNT&id=" + accountId);

         dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/account.jsp");
        dispatcher.forward(request, response);
    }
}
