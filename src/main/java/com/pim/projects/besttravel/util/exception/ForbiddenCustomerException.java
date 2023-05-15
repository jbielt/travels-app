package com.pim.projects.besttravel.util.exception;

public class ForbiddenCustomerException extends RuntimeException{

    public ForbiddenCustomerException(){
        super("This customer is blocked");
    }
}
