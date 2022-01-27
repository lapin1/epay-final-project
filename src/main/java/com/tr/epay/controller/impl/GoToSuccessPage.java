package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
import com.tr.epay.service.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToSuccessPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regSuccessPage.jsp");
        dispatcher.forward(request, response);
    }
}
