package com.shop.myfile.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;

public class S3Exception extends AmazonS3Exception {
    private String code;
    private String message;

    public S3Exception(CustomFileExceptionCode code){
        super(code.getMessage());
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
