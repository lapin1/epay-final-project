package com.tr.epay.service;

public class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

}
