package com.tr.epay.dao;

import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    boolean authorization(String login, String password) throws DAOException;

    void registration(User user, UserInfo userInfo) throws DAOException, SQLException;

    int getIdOfAuthorizedUser(String login) throws DAOException;

    String getRoleOfUserById(int userId) throws DAOException;
    String getNameOfUser(int id) throws DAOException;

    List<User> viewAllUsers() throws DAOException, SQLException;

}
