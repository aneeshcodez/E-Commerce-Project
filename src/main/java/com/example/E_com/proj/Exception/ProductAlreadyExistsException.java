package com.example.E_com.proj.Exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String msg){
        super(msg);
    }
    public ProductAlreadyExistsException(){}

}
