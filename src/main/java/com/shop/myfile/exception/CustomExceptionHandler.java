package com.shop.myfile.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.shop.myfile.exception.CustomFileExceptionCode.IO_EXCEPTION;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    public static void errorLogging(Exception e, HttpServletRequest request) {
        log.error(e.getClass().getSimpleName() + " occured");
        log.error("Request URI : {}", request.getRequestURI());
        log.error("Request Method : {}", request.getMethod());
        log.error(e.getCause().toString());
    }


    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ExceptionResponse> handleIOException(IOException e, HttpServletRequest request) {
        errorLogging(e, request);
        return ResponseEntity
                .badRequest()
                .body(
                        ExceptionResponse
                                .builder()
                                .code(IO_EXCEPTION.getCode())
                                .message(IO_EXCEPTION.getMessage())
                                .detail(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(S3Exception.class)
    protected ResponseEntity<ExceptionResponse> handleS3Exception(S3Exception e, HttpServletRequest request) {
        errorLogging(e, request);
        return ResponseEntity
                .badRequest()
                .body(
                        ExceptionResponse
                                .builder()
                                .code(e.getCode())
                                .message(e.getMessage())
                                .detail(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(CustomFileUploadException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomFileUploadException(CustomFileUploadException e, HttpServletRequest request) {
        errorLogging(e, request);
        return ResponseEntity
                .badRequest()
                .body(
                        ExceptionResponse
                                .builder()
                                .code(e.getCode())
                                .message(e.getMessage())
                                .detail(e.getMessage())
                                .build()
                );
    }
}
