package com.tr.epay.main;

import com.tr.epay.dao.*;
import com.tr.epay.dao.impl.SQLTransactionDAO;
import com.tr.epay.service.*;
import com.tr.epay.service.impl.SQLTransactionService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ServiceException, DAOException, SQLException, ValidationException {

        TransactionDAO transactionDAO = new SQLTransactionDAO();
        TransactionService transactionService = new SQLTransactionService();
        System.out.println(transactionDAO.showAccountTransactionsById(1));
        //System.out.println(transactionDAO.showTransactionsById(1));
        //System.out.println(validator.getErrors());
       // User user = new User("loginTestWEB1", "passwordTestWEB1");
       // UserInfo userInfo = new UserInfo("firstNameTestWEB1", "lastNameTestWEB1", "cityTestWEB1", "streetTestWEB1", "stateTestWEB1", "zipTestWEB1", "phoneTestWEB1", "emailtestWEB1");
//
       // DAOProvider provider = DAOProvider.getInstance();
       // UserDAO userDAO = provider.getUserDAO();
       // AccountDAO accountDAO = new SQLAccountDAO();
       //AccountDAO accountDAO1 = provider.getAccountDAO();

        //CardDAO cardDAO = new SQLCardDAO();
        ////System.out.println(cardDAO.showCardsByUserId(1));
        //System.out.println(cardDAO.selectCard(1));
        //System.out.println("YOU ARE THE BEST");

        //ServiceProvider serviceProvider = ServiceProvider.getInstance();
        //CardService cardService = serviceProvider.getCardService();
        //TransactionDAO transactionDAO = new SQLTransactionDAO();
        //transactionDAO.transferFromAccountToAccount("BY06MMBN301200001111", "UF03GKUN809000000809", 10000);
        //System.out.println(cardService.showCardsByAccountId(2));
        //System.out.println(accountDAO1.selectAccount(1));
        //transactionDAO.transferFromAccountToCard("BY06MMBN301200001111", "4929710817819521","2025-10-20", 4400);
        //PayByCardTest test = new PayByCardTest();
        //test.transferFromCardToAccount(8);
        //ServiceProvider provider = ServiceProvider.getInstance();
//UserService userService = provider.getUserService();
        //System.out.println(userService.viewAllUsers());
       //Account account = new Account();
       //accountService.createAccount(account);
       //System.out.println(account);
//AccountService accountService = provider.getAccountService();
//accountService.blockAccount(1);




        //Transaction transaction = new Transaction((java.sql.Date) date,"1","2",500,"executed",1,1 );
        //transactionDAO.addTransaction(transaction);
        //UserService userService = new SQLUserService();

        //userService.registration(user, userInfo);
        //System.out.println(userService.authorization("lapin", "root"));


    }
}
