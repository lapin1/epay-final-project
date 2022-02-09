package com.tr.paymentTest;

import com.tr.epay.dao.AccountDAO;
import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.dao.PaymentDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;


public class TransferToAccountTest {
    private final DAOProvider provider = DAOProvider.getInstance();
    private final AccountDAO accountDAO = provider.getAccountDAO();
    private final PaymentDAO paymentDAO = provider.getPaymentDAO();

    //need to change database properties file before testing open README


    @Test
    public void transferToAccountFromAccountTest() throws DAOException, SQLException {
        String accountFrom = "5120652";
        String accountTo = "2222222";

        double expectedAccountBalance = (accountDAO.getBalanceOfAccount(accountTo) + 4000);
        paymentDAO.transferFromAccountToAccount(accountFrom, accountTo, 4000);
        double accountBalanceAfterTransfer = accountDAO.getBalanceOfAccount(accountTo);
        Assert.assertEquals(accountBalanceAfterTransfer, expectedAccountBalance,0.00000001);


    }
}
