package com.kmbl.InventoryManagementService.exceptions;

public class ServiceException extends Exception{
    private Type exceptionType;
    public enum Type{
        PARSING_EXCEPTION,BAD_REQUEST;
    }

    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Type type,String message) {
        super(message);
        this.exceptionType = type;
    }
}
