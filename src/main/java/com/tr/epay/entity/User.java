package com.tr.epay.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String login;
    private String password;
    private int roleId;
    private int userInfoId;

    public User() {

    }

    public User(int id, String login, String password, int roleId, int userInfoId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.userInfoId = userInfoId;
    }

    public User(String login, String password, int roleId, int userInfoId) {
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.userInfoId = userInfoId;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && roleId == user.roleId && userInfoId == user.userInfoId && login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roleId, userInfoId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", userInfoId=" + userInfoId +
                '}';
    }
}



