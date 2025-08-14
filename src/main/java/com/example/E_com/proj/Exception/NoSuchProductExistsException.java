package com.example.E_com.proj.Exception;

public class NoSuchProductExistsException extends RuntimeException{
    public NoSuchProductExistsException(String msg){
        super(msg);
    }
    public NoSuchProductExistsException(){}
}
