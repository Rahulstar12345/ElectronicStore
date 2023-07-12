package com.bikkadit.electronic.store.exception;

public class BadApiRequestException extends RuntimeException{

    public BadApiRequestException(String message){
        super(message);
    }

    public BadApiRequestException(){
        super("Bad Request !!");
    }

}
