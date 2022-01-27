package com.tr.epay.dao.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.dao.UserDAO;
import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String INSERT_USER = "INSERT INTO users (login, password, roles_id, userInfo_id) VALUES (?, ?, ?, ?)";
    private static final String INSERT_USER_INFO = "INSERT INTO user_info (first_name, last_name, city, street, state, zip, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER = "SELECT * FROM USERS users WHERE login = ? AND password = ?";
    private static final String SELECT_ID_OF_AUTHORIZED_USER = "SELECT id FROM users WHERE login = ?";
    private static final String SHOW_USERS = "SELECT * FROM users";
    private static final String GET_ROLE_OF_USER = "select role_name from roles where id = (SELECT roles_id from users where id = ?)";
    private static final String GET_NAME_OF_USER = "select first_name from user_info where id = ?";


    @Override
    public boolean authorization(String login, String password) throws DAOException {
        boolean status;
        Connection connection = null;
        PreparedStatement psUser = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            psUser = connection.prepareStatement(SELECT_USER);
            psUser.setString(1, login);
            psUser.setString(2, password);
            rs = psUser.executeQuery();


            status = rs.next();


        }catch (ConnectionPoolException | SQLException ex){

            throw new DAOException("Error selecting user from database", ex);  // throws DAOException

        }finally {
            if (connection != null) {
                pool.closeConnection(connection, psUser, rs);
            }
        }

        return status;
    }

    @Override
    public void registration(User user, UserInfo userInfo) throws DAOException, SQLException {
        int idInfo = 0;
        Connection connection = null;
        PreparedStatement psUser = null;
        PreparedStatement psUserInfo = null;
        ResultSet rs = null;
        try {

            connection = pool.takeConnection();

            connection.setAutoCommit(false);

            psUserInfo = connection.prepareStatement(INSERT_USER_INFO, PreparedStatement.RETURN_GENERATED_KEYS);
            psUser = connection.prepareStatement(INSERT_USER);


            psUserInfo.setString(1,userInfo.getFirstName());
            psUserInfo.setString(2, userInfo.getLastName());
            psUserInfo.setString(3,userInfo.getCity());
            psUserInfo.setString(4,userInfo.getStreet());
            psUserInfo.setString(5,userInfo.getState());
            psUserInfo.setString(6,userInfo.getZip());
            psUserInfo.setString(7,userInfo.getPhone());
            psUserInfo.setString(8,userInfo.getEmail());
            psUserInfo.executeUpdate();

            rs = psUserInfo.getGeneratedKeys();
            if(rs.next()){
                idInfo = rs.getInt(1);
            }

            psUser.setString(1,user.getLogin());
            psUser.setString(2,user.getPassword());
            psUser.setInt(3,1);  //1 - client
            psUser.setInt(4, idInfo);

            psUser.executeUpdate();

            connection.commit();


        }catch (ConnectionPoolException | SQLException ex){
            if (connection != null) {
                connection.rollback();
            }
            throw new DAOException("Error inserting user to database", ex);  // throws DAOException

        }finally {
            if (connection != null) {
                pool.closeConnection(connection, psUser, psUserInfo, rs);
            }
        }

    }

    @Override
    public int getIdOfAuthorizedUser(String login) throws DAOException {
        int userId = 0;
        Connection connection = null;
        PreparedStatement psUser = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            psUser = connection.prepareStatement(SELECT_ID_OF_AUTHORIZED_USER);
            psUser.setString(1, login);

            rs = psUser.executeQuery();

            if (rs.next()){
                userId = rs.getInt(1);
            }


        }catch (ConnectionPoolException | SQLException ex){

            throw new DAOException("Error selecting user from database", ex);  // throws DAOException

        }finally {
            if (connection != null) {
                pool.closeConnection(connection, psUser, rs);
            }
        }

        return userId;
    }

    @Override
    public String getRoleOfUserById(int userId) throws DAOException {
        String roleName = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ROLE_OF_USER);
            preparedStatement.setInt(1, userId);

            rs = preparedStatement.executeQuery();

            if (rs.next()){
                roleName = rs.getString(1);
            }


        }catch (ConnectionPoolException | SQLException ex){

            throw new DAOException("Error selecting user from database", ex);  // throws DAOException

        }finally {
            if (connection != null) {
                pool.closeConnection(connection, preparedStatement, rs);
            }
        }
        return roleName;
    }

    @Override
    public String getNameOfUser(int userId) throws DAOException {
        String userName = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_NAME_OF_USER);
            preparedStatement.setInt(1, userId);

            rs = preparedStatement.executeQuery();

            if (rs.next()){
                userName = rs.getString(1);
            }


        }catch (ConnectionPoolException | SQLException ex){

            throw new DAOException("Error selecting user from database", ex);  // throws DAOException

        }finally {
            if (connection != null) {
                pool.closeConnection(connection, preparedStatement, rs);
            }
        }
        return userName;
    }

    @Override
    public List<User> viewAllUsers() throws DAOException, SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user;
        try {
            connection = pool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SHOW_USERS);

            while (resultSet.next()){

                user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRoleId(resultSet.getInt(4));
                user.setUserInfoId(resultSet.getInt(5));

                users.add(user);

            }
        }catch (ConnectionPoolException | SQLException ex){
            throw new DAOException("Error querying users from database", ex);  // throws DAOException
        }finally {
            if (connection != null) {
                pool.closeConnection(connection, statement, resultSet);
            }
        }
        return users;
    }


}


