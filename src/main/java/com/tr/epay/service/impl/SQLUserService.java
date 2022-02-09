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


    @Override
    public boolean authorization(String login, String password) throws ServiceException, ValidationException {
        boolean result;
        UserValidator userValidator = ValidatorProvider.getInstance().getUserValidator();
        Map<String, String> errors = userValidator.validateSignIn(login, password);

        if(!errors.isEmpty()){

                log.error(ErrorMessage.VALIDATION_ERROR);
                throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
            }

        try {
            UserDAO userDAO = provider.getUserDAO();
            result = userDAO.authorization(login,password);

        }catch (DAOException ex) {
            log.error(LoggerMessage.ERROR_SELECTING_USER, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
        return result;
    }

    @Override
    public void registration(User user, UserInfo userInfo) throws ServiceException, ValidationException {
        UserValidator userValidator = ValidatorProvider.getInstance().getUserValidator();
        Map<String, String> errors = userValidator.validateSignUp(user, userInfo);

        if(!errors.isEmpty()){
            log.error(ErrorMessage.VALIDATION_ERROR);
            throw new ValidationException(ErrorMessage.VALIDATION_ERROR, errors);
        }

        try {
            UserDAO userDAO = provider.getUserDAO();
            userDAO.registration(user, userInfo);
        }catch (DAOException | SQLException ex){
            log.error(LoggerMessage.ERROR_INSERTING_USER, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
    }

    @Override
    public int getIdOfAuthorizedUser(String login) throws ServiceException {
        int userId;
        try {
            UserDAO userDAO = provider.getUserDAO();
            userId =  userDAO.getIdOfAuthorizedUser(login);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_ID_USER, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        return userId;
    }

    @Override
    public String getRoleOfUser(int id) throws ServiceException {
        String roleName;
        try {
            UserDAO userDAO = provider.getUserDAO();
            roleName =  userDAO.getRoleOfUserById(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_ROLE_OF_USER, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        return roleName;
    }

    @Override
    public String getNameOfUser(int id) throws ServiceException {
        String userName;
        try {
            UserDAO userDAO = provider.getUserDAO();
            userName = userDAO.getNameOfUser(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_GETTING_USER_NAME, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        return userName;
    }


    @Override
    public List<User> viewAllUsers() throws ServiceException {
        List<User> users;
        try {
            UserDAO userDAO = provider.getUserDAO();
            users =  userDAO.viewAllUsers();
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SELECTING_USER, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }

        return users;
    }

    @Override
    public UserInfo showUserInformation(int id) throws ServiceException {
        UserInfo userInfo;
        try {
            UserDAO userDAO = provider.getUserDAO();
            userInfo = userDAO.showUserInformationById(id);
        }catch (DAOException ex){
            log.error(LoggerMessage.ERROR_SHOWING_USER_INFORMATION, ex);
            throw new ServiceException(ErrorMessage.DAO_LAYER_ERROR, ex);
        }
        return userInfo;
    }

    @Override
    public boolean updateUserInformation(UserInfo userInfo) throws ServiceException {
        return false;
    }


}
