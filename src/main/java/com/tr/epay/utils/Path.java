package com.tr.epay.utils;

public class Path {
    public static final String GO_TO_HOME_PAGE = "MyController?command=GO_TO_HOME_PAGE";
    public static final String GO_TO_SELECTED_ACCOUNT = "MyController?command=GO_TO_SELECTED_ACCOUNT&id=";
    public static final String GO_TO_AUTHORIZATION_PAGE = "MyController?command=GO_TO_AUTHORIZATION_PAGE";
    public static final String GO_TO_REGISTRATION_PAGE = "MyController?command=GO_TO_REGISTRATION_PAGE";
    public static final String GO_TO_REGISTRATION_SUCCESS_PAGE = "MyController?command=GO_TO_REGISTRATION_SUCCESS_PAGE";

    public static final String AUTHORIZATION = "/WEB-INF/jsp/authorization.jsp";
    public static final String REGISTRATION = "/WEB-INF/jsp/registration.jsp";
    public static final String REGISTRATION_SUCCESS_PAGE = "/WEB-INF/jsp/regSuccessPage.jsp";
    public static final String ACCOUNT = "/WEB-INF/jsp/account.jsp";
    public static final String USER_ACCOUNTS = "WEB-INF/jsp/userAccounts.jsp";
    public static final String USER_HOME = "WEB-INF/jsp/userHome.jsp";
    public static final String ADMIN_HOME = "WEB-INF/jsp/adminHome.jsp";
    public static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
}
