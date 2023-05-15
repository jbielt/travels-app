package com.pim.projects.besttravel.util.exception;

public class IdNotFoundException extends RuntimeException{


    private static final String ERROR_MESSAGE = "Record don't exist in %s";

    public IdNotFoundException(String tableName){
        super(String.format(ERROR_MESSAGE, tableName));
    }
}
