package com.tr.epay.dao.impl;

import com.tr.epay.dao.DAOException;
import com.tr.epay.dao.pool.ConnectionPool;
import com.tr.epay.dao.pool.ConnectionPoolException;
import com.tr.epay.dao.UserDAO;
import com.tr.epay.entity.User;
import com.tr.epay.entity.UserInfo;
import com.tr.epay.utils.ErrorMessage;
import com.tr.epay.utils.LoggerMessage;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {
    private static final Logger log = Logger.getLogger(SQLUserDAO.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String INSERT_USER = "INSERT INTO users (login, password, roles_id, userInfo_id) VALUES (?, ?, ?, ?)";
    private static final String INSERT_USER_INFO = "INSERT INTO user_info (first_name, last_name, city, street, state, zip, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER = "SELECT * FROM USERS users WHERE login = ? AND password = ?";
    private static final String SELECT_ID_OF_AUTHORIZED_USER = "SELECT id FROM users WHERE login = ?";
    private static final String SHOW_USERS = "SELECT * FROM users";
    private static final String GET_ROLE_OF_USER = "SELECT role_name FROM roles WHERE id = (SELECT roles_id FROM users WHERE id = ?)";
    private static final String GET_NAME_OF_USER = "SELECT first_name FROM user_info WHERE id = ?";
    private static final String SHOW_USER_INFORMATION_BY_ID = "SELECT id, first_name, last_name, city, street, state, zip, phone, email FROM user_info WHERE id = ?";
    private static final String UPDATE_USER_INFORMATION = "UPDATE user_info SET first_name = ?, last_name = ?, city = ?, street = ?, state = ?, zip = ?, phone = ?, email = ? WHERE id = ?";


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
            log.error(LoggerMessage.ERROR_SELECTING_USER + login);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, psUser, rs);
            }
        }
        return status;
    }

    @Override
    public void registration(User user, UserInfo userInfo) throws DAOException, SQLException {
        int idInfo = 0;
        int clientID = 1;
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
            psUser.setInt(3,clientID);  //1 - client
            psUser.setInt(4, idInfo);

            psUser.executeUpdate();

            connection.commit();


        }catch (ConnectionPoolException | SQLException ex){
            if (connection != null) {
                connection.rollback();
            }
            log.error(LoggerMessage.ERROR_INSERTING_USER + user.getLogin());
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

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

            log.error(LoggerMessage.ERROR_GETTING_ID_USER + login);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

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

            log.error(LoggerMessage.ERROR_GETTING_ROLE_OF_USER + userId);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

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

            log.error(LoggerMessage.ERROR_GETTING_USER_NAME + userId);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);

        }finally {
            if (connection != null) {
                pool.closeConnection(connection, preparedStatement, rs);
            }
        }
        return userName;
    }

    @Override
    public List<User> viewAllUsers() throws DAOException{
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
            log.error(LoggerMessage.ERROR_GETTING_USERS);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if (connection != null) {
                pool.closeConnection(connection, statement, resultSet);
            }
        }
        return users;
    }

    @Override
    public UserInfo showUserInformationById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserInfo userInfo = null;
        try {
            connection = pool.takeConnection();

            preparedStatement = connection.prepareStatement(SHOW_USER_INFORMATION_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt(1));
                userInfo.setFirstName(resultSet.getString(2));
                userInfo.setLastName(resultSet.getString(3));
                userInfo.setCity(resultSet.getString(4));
                userInfo.setStreet(resultSet.getString(5));
                userInfo.setState(resultSet.getString(6));
                userInfo.setZip(resultSet.getString(7));
                userInfo.setPhone(resultSet.getString(8));
                userInfo.setEmail(resultSet.getString(9));


            }
        }catch (ConnectionPoolException | SQLException ex){
            log.error(LoggerMessage.ERROR_GETTING_USER_INFORMATION + id);
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if (connection != null) {
                pool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return userInfo;
    }

    @Override
    public boolean updateUserInformation(UserInfo userInfo) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdated;

        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_INFORMATION);

            preparedStatement.setString(1, userInfo.getFirstName());
            preparedStatement.setString(2,userInfo.getLastName());
            preparedStatement.setString(3,userInfo.getCity());
            preparedStatement.setString(4,userInfo.getStreet());
            preparedStatement.setString(5,userInfo.getState());
            preparedStatement.setString(6,userInfo.getZip());
            preparedStatement.setString(7,userInfo.getPhone());
            preparedStatement.setString(8,userInfo.getEmail());

            preparedStatement.setInt(9,userInfo.getId()); //from jsp

            isUpdated = preparedStatement.executeUpdate() > 0;

        }catch (ConnectionPoolException | SQLException ex){
            log.error(LoggerMessage.ERROR_UPDATING_USER_INFORMATION + userInfo.getId());
            throw new DAOException(ErrorMessage.ERROR_WITH_DATABASE, ex);
        }finally {
            if(connection != null){
                pool.closeConnection(connection, preparedStatement);
            }
        }
        return isUpdated;
    }


}


