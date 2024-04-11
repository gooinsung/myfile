package com.shop.myfile.exception;

import lombok.Getter;

@Getter
public class CustomFileException extends Exception{

    private String code;
    private String message;

    public CustomFileException(CustomFileExceptionCode code){
        super(code.getMessage());
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
