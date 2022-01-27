package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.dao.DAOException;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException, ServiceException, ValidationException {
        String locale = request.getParameter("locale");
        request.getSession().setAttribute("locale", locale);

        String url = (String) request.getSession().getAttribute("url");


        response.sendRedirect(url);


    }
}
