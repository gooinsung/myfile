package com.shop.myfile.exception;

import lombok.Getter;

@Getter
public class CustomFileUploadException extends Exception{

    private String code;
    private String message;

    public CustomFileUploadException(CustomFileExceptionCode code){
        super(code.getMessage());
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
