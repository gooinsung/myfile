package com.shop.myfile.exception;

import lombok.Getter;

@Getter
public enum CustomFileExceptionCode {

    SUCCESS("S000", "Success to save files."),
    FAIL("E001", "Fail to save files."),
    IO_EXCEPTION("E002", "There is an IOException"),
    S3_EXCEPTION("E003", "S3 Exception Occur.");
    ;
    private String code;
    private String message;

    CustomFileExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
