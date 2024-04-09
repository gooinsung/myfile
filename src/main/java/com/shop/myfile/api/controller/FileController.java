package com.shop.myfile.api.controller;

import com.shop.myfile.api.dto.request.MyshopSingleFileUploadRequestDto;
import com.shop.myfile.api.dto.response.MyshopSingleFileUploadResponseDto;
import com.shop.myfile.api.service.MyshopS3Service;
import com.shop.myfile.exception.CustomFileUploadException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final MyshopS3Service myshopS3Service;
    @PostMapping
    public MyshopSingleFileUploadResponseDto uploadFile(@ModelAttribute MyshopSingleFileUploadRequestDto requestDto, HttpServletRequest request)
            throws CustomFileUploadException {
        log.info("Upload file API start.");
        log.info("MyshopSingleFileRequestDto : {}", requestDto);
        return myshopS3Service.upload(requestDto);
    }
}
