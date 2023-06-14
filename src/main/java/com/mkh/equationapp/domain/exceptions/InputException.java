package com.mkh.equationapp.domain.exceptions;

public class InputException extends RuntimeException{
    public InputException(){
    }

    public InputException(String message){
        super(message);
    }
}
