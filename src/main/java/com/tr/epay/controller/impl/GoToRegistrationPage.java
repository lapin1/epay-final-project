package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.utils.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRegistrationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.REGISTRATION);
        dispatcher.forward(request, response);
    }
}
