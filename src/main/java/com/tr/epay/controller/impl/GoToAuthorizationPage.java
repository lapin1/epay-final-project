package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToAuthorizationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.setAttribute(AttributeName.URL, Path.GO_TO_AUTHORIZATION_PAGE);

        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.AUTHORIZATION);
        dispatcher.forward(request, response);
    }
}
