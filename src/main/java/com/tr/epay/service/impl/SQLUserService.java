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

import java.sql.SQLException;
import java.util.List;

public class SQLUserService implements UserService {
    DAOProvider provider = DAOProvider.getInstance();
    UserDAO userDAO = provider.getUserDAO();
    UserValidator validator = new UserValidator();  // getInstance

    @Override
    public boolean authorization(String login, String password) throws ServiceException, ValidationException {
        boolean result;
        List<String> errors = validator.validate(login, password);

        if(!errors.isEmpty()){
                throw new ValidationException("validation error", errors);
            }


        try {
            result = userDAO.authorization(login,password);

        }catch (DAOException ex) {
            throw new ServiceException("Error authorizing user", ex);
        }
        return result;
    }

    @Override
    public void registration(User user, UserInfo userInfo) throws ServiceException {

        try {
            userDAO.registration(user, userInfo);
        }catch (DAOException | SQLException ex){
            throw new ServiceException("Error in registration of new user", ex);
        }
    }

    @Override
    public int getIdOfAuthorizedUser(String login) throws ServiceException {
        int userId;
        try {
            userId =  userDAO.getIdOfAuthorizedUser(login);
        }catch (DAOException ex){
            throw new ServiceException("Error getting id of user", ex);
        }

        return userId;
    }

    @Override
    public String getRoleOfUser(int id) throws ServiceException {
        String roleName;
        try {
            roleName =  userDAO.getRoleOfUserById(id);
        }catch (DAOException ex){
            throw new ServiceException("Error getting role of user", ex);
        }

        return roleName;
    }

    @Override
    public String getNameOfUser(int id) throws ServiceException {
        String userName;
        try {
            userName = userDAO.getNameOfUser(id);
        }catch (DAOException ex){
            throw new ServiceException("Error getting name of user", ex);
        }

        return userName;
    }


    @Override
    public List<User> viewAllUsers() throws ServiceException {
        List<User> users;
        try {
            users =  userDAO.viewAllUsers();
        }catch (DAOException | SQLException ex){
            throw new ServiceException("Error getting users", ex);
        }

        return users;
    }
}
