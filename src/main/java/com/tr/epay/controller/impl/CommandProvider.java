package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.controller.impl.todelete.GoToPaymentPage;
import com.tr.epay.controller.impl.todelete.ShowMyCards;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandProvider(){
        commands.put("authorization", new AuthorizationCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("accountCreation", new AccountCreation());
        commands.put("showMyCards", new ShowMyCards());
        commands.put("transferToAccount", new TransferToAccountCommand());
        commands.put("transferToCard", new TransferToCardCommand());
        commands.put("topUpAccount", new TopUpAccountCommand());
        commands.put("addCard", new AddCardCommand());
        commands.put("transferByCardToAccount", new TransferByCardToAccount());
        commands.put("blockAccount", new BlockAccountCommand());
        commands.put("unblock", new UnblockAccountCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("goToUsersAccount", new GoToUsersAccount());
        commands.put("changeLanguage", new ChangeLanguage());


        commands.put("GO_TO_REGISTRATION_PAGE", new GoToRegistrationPageCommand());
        commands.put("GO_TO_AUTHORIZATION_PAGE", new GoToAuthorizationPageCommand());
        commands.put("GO_TO_HOME_PAGE", new GoToHomePageCommand());
        commands.put("GO_TO_SUCCESS_PAGE", new GoToSuccessPage());
        commands.put("GO_TO_SELECTED_ACCOUNT", new GoToSelectedAccount());
        commands.put("GO_TO_PAYMENT_PAGE", new GoToPaymentPage());
    }

    public Command getCommand(String commandName){   //final
        Command command = commands.get(commandName);
        return command;
    }
}
