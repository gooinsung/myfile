package com.shop.myfile.exception;

import lombok.Getter;

@Getter
public enum CustomFileExceptionCode {

    SUCCESS("S000", "Success to save files."),
    FAIL("E001", "Fail to save files."),
    IO_EXCEPTION("E002", "There is an IOException"),
    S3_EXCEPTION("E003", "S3 Exception Occur."),
    FILE_EMPTY_EXCEPTION("E004", "File is empty."),
    BUCKET_NAME_EXCEPTION("E005","There is no bucket name"),
    FILE_PATH_EXCEPTION("E006", "Invalidated file path."),

    FILE_DELETE_EXCEPTION("E100", "Fail to delete file"),

    ;
    private String code;
    private String message;

    CustomFileExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
