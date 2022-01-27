package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToAuthorizationPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.setAttribute("url", "MyController?command=GO_TO_AUTHORIZATION_PAGE");


        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorization.jsp");
        dispatcher.forward(request, response);
    }
}
