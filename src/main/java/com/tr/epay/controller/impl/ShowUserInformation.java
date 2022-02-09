package com.tr.epay.controller.impl;

import com.tr.epay.controller.Command;
import com.tr.epay.entity.UserInfo;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.ServiceProvider;
import com.tr.epay.service.UserService;
import com.tr.epay.utils.AttributeName;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.ParameterName;
import com.tr.epay.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowUserInformation implements Command {
    private static final Logger log = Logger.getLogger(ShowUserInformation.class);
    private final ServiceProvider provider = ServiceProvider.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {

        HttpSession session = request.getSession();

        String login = (String) session.getAttribute(AttributeName.LOGIN_OF_USER);
        UserInfo userInfo;

        int id = Integer.parseInt(request.getParameter(ParameterName.ID));

        try {
            UserService userService = provider.getUserService();
            userInfo = userService.showUserInformation(id);
            request.setAttribute("userInfo", userInfo);
            request.setAttribute("login", login);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userInformation.jsp");
            dispatcher.forward(request, response);

            response.sendRedirect(Path.GO_TO_AUTHORIZATION_PAGE);
        }catch (ServiceException ex){
            log.error(ErrorMessage.SERVICE_LAYER_ERROR, ex);
            response.sendRedirect(Path.USER_ACCOUNTS);
        }
    }
}
