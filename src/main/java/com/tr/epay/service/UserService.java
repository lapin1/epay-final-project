package com.tr.epay.service;

import com.tr.epay.dao.DAOException;
import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;

import java.util.List;

public interface UserService {

    boolean authorization(String login, String password) throws DAOException, ServiceException, ValidationException;
    void registration(User user, UserInfo userInfo) throws  ServiceException;
    int getIdOfAuthorizedUser(String login) throws ServiceException;
    String getRoleOfUser(int id) throws ServiceException;
    String getNameOfUser(int id) throws ServiceException;
    List<User> viewAllUsers() throws ServiceException;

}