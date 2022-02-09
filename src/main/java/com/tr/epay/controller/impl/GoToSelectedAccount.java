package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.Account;
import com.tr.epay.entity.Card;
import com.tr.epay.entity.Transaction;
import com.tr.epay.service.*;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.ParameterName;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GoToSelectedAccount implements Command {
    private static final Logger log = Logger.getLogger(GoToSelectedAccount.class);
    private final ServiceProvider serviceProvider = ServiceProvider.getInstance();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.getAttribute(AttributeName.LOGIN_OF_USER);
        String login = (String) session.getAttribute(AttributeName.LOGIN_OF_USER);
        String roleAdmin = "administrator";
        String role = (String) session.getAttribute(AttributeName.ROLE_OF_USER);
        Account account = null;
        String num = null;
        List<Card> cards = null;
        List<Transaction> accTransactions = null;
        String errorMessage = "Something error, please go back to main";
        String errorMessage1 = "permission denied";

        int accountId = Integer.parseInt(request.getParameter(ParameterName.ID));
        RequestDispatcher dispatcher;

        if(login == null || role.equals(roleAdmin)){

            request.setAttribute(AttributeName.ERROR_MESSAGE, errorMessage1);
            dispatcher = request.getRequestDispatcher(Path.AUTHORIZATION);
            dispatcher.forward(request, response);
        }

        try{

            AccountService accountService = serviceProvider.getAccountService();
            account = accountService.selectAccount(accountId);
            num = account.getAccountNumber();
            session.setAttribute(AttributeName.SELECTED_ACCOUNT_NUMBER,account.getAccountNumber());
            session.setAttribute(AttributeName.SELECTED_ACCOUNT_BALANCE,account.getBalance());
            session.setAttribute(AttributeName.SELECTED_ACCOUNT_ID, account.getId());

        }catch (ServiceException ex){

            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            request.setAttribute(AttributeName.ERROR_MESSAGE, errorMessage);
            dispatcher = request.getRequestDispatcher(Path.ERROR_PAGE);
            dispatcher.forward(request, response);
        }

        request.setAttribute(AttributeName.SELECTED_ACCOUNT, account);

         try{

             TransactionService transactionService = serviceProvider.getListTransactionService();
             CardService cardService = serviceProvider.getCardService();
             cards = cardService.showCardsByAccountId(accountId);
             accTransactions = transactionService.showAccountTransactionsById(num);

         }catch (ServiceException ex){

             log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
             request.setAttribute(AttributeName.ERROR_MESSAGE, errorMessage);
             dispatcher = request.getRequestDispatcher(Path.ERROR_PAGE);
             dispatcher.forward(request, response);

         }

        session.setAttribute(AttributeName.ACCOUNT_TRANSACTIONS, accTransactions);
        session.setAttribute(AttributeName.CARDS, cards);
        session.setAttribute(AttributeName.URL, Path.GO_TO_SELECTED_ACCOUNT + accountId);

        dispatcher = request.getRequestDispatcher(Path.ACCOUNT);
        dispatcher.forward(request, response);
    }
}
