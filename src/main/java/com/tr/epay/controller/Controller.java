package com.tr.epay.controller;

import com.tr.epay.controller.impl.CommandProvider;
import com.tr.epay.dao.DAOException;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ValidationException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CommandProvider provider = new CommandProvider();

    public Controller(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       process(request, response);
    }

    // любой реквест который к нам приходит
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // приносит значение команда
        String commandName = request.getParameter("command");

        //по которому из хранилища команд достаем нужный обьект выполняющий эту команду
        Command command = provider.getCommand(commandName);

        try {
            command.execute(request, response);
        } catch (DAOException | ServiceException | ValidationException e) {
            e.printStackTrace();
        }

    }
}
