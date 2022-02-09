package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.utils.CommandName;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandProvider(){
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.CREATE_ACCOUNT, new AccountCreationCommand());
        commands.put(CommandName.TRANSFER_TO_ACCOUNT, new TransferToAccountCommand());
        commands.put(CommandName.TRANSFER_TO_CARD, new TransferToCardCommand());
        commands.put(CommandName.TOP_UP_ACCOUNT, new TopUpAccountCommand());
        commands.put(CommandName.ADD_CARD, new AddCardCommand());
        commands.put(CommandName.TRANSFER_BY_CARD_TO_ACCOUNT, new TransferByCardToAccount());
        commands.put(CommandName.BLOCK_ACCOUNT, new BlockAccountCommand());
        commands.put(CommandName.UNBLOCK_ACCOUNT, new UnblockAccountCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
        commands.put(CommandName.DELETE_CARD, new DeleteCardCommand());
        commands.put(CommandName.TRANSFER_BY_CARD_TO_CARD, new TransferByCardToCardCommand());
        commands.put(CommandName.SHOW_USER_INFORMATION, new ShowUserInformation());


        commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
        commands.put(CommandName.GO_TO_AUTHORIZATION_PAGE, new GoToAuthorizationPage());
        commands.put(CommandName.GO_TO_HOME_PAGE, new GoToHomePage());
        commands.put(CommandName.GO_TO_SUCCESS_PAGE, new GoToSuccessPage());
        commands.put(CommandName.GO_TO_SELECTED_ACCOUNT, new GoToSelectedAccount());
        commands.put(CommandName.GO_TO_USERS_ACCOUNT, new GoToUsersAccount());
    }

    public Command getCommand(String commandName){
        Command command = commands.get(commandName);
        return command;
    }
}
