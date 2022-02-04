package com.tr.epay.service.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.DAOProvider;
import com.tr.epay.dao.UserDAO;
import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;
import com.tr.epay.service.ServiceException;
import com.tr.epay.service.UserService;
import com.tr.epay.service.ValidationException;
import com.tr.epay.service.impl.validator.UserValidator;
import com.tr.epay.service.impl.validator.ValidatorProvider;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SQLUserService implements UserService {
    private static final Logger log = Logger.getLogger(SQLUserService.class);
    DAOProvider provider = DAOProvider.getInstance();
    UserDAO userDAO = provider.getUserDAO();
    UserValidator userValidator = ValidatorProvider.getInstance().getUserValidator();

    @Override
    public boolean authorization(String login, String password) throws ServiceException, ValidationException {
        boolean result;
        Map<String, String> errors = userValidator.validateSignIn(login, password);

        if(!errors.isEmpty()){

                log.error(ErrorMessage.VALIDATION_ERROR);
                throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
            }

        try {
            result = userDAO.authorization(login,password);

        }catch (DAOException ex) {
            log.error(LoggerMessage.ERROR_SELECTING_USER);
            throw new ServiceException(LoggerMessage.ERROR_SELECTING_USER, ex);
        }
        return result;
    }

    @Override
    public void registration(User user, UserInfo userInfo) throws ServiceException, ValidationException {
        Map<String, String> errors = userValidator.validateSignUp(user, userInfo);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try {
            userDAO.registration(user, userInfo);
        }catch (DAOException | SQLException ex){
            log.error(LoggerMessage.ERROR_INSERTING_USER);
            throw new ServiceException(LoggerMessage.ERROR_INSERTING_USER, ex);
        }
    }

    @Override
    public int getIdOfAuthorizedUser(String login) throws ServiceException {
        int userId;
        try {
            userId =  userDAO.getIdOfAuthorizedUser(login);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_ID_USER);
            throw new ServiceException(LoggerMessage.ERROR_GETTING_ID_USER, ex);
        }

        return userId;
    }

    @Override
    public String getRoleOfUser(int id) throws ServiceException {
        String roleName;
        try {
            roleName =  userDAO.getRoleOfUserById(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_ROLE_OF_USER);
            throw new ServiceException(LoggerMessage.ERROR_GETTING_ROLE_OF_USER, ex);
        }

        return roleName;
    }

    @Override
    public String getNameOfUser(int id) throws ServiceException {
        String userName;
        try {
            userName = userDAO.getNameOfUser(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_USER_NAME);
            throw new ServiceException(LoggerMessage.ERROR_GETTING_USER_NAME, ex);
        }

        return userName;
    }


    @Override
    public List<User> viewAllUsers() throws ServiceException {
        List<User> users;
        try {
            users =  userDAO.viewAllUsers();
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_USER);
            throw new ServiceException(LoggerMessage.ERROR_SELECTING_USER, ex);
        }

        return users;
    }
}
