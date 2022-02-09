package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.utils.AttributeName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String locale = request.getParameter("locale");
        request.getSession().setAttribute(AttributeName.LOCALE, locale);

        String url = (String) request.getSession().getAttribute(AttributeName.URL);
        response.sendRedirect(url);


    }
}
