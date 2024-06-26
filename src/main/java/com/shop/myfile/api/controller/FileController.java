package com.shop.myfile.api.controller;

import com.shop.myfile.api.dto.request.MyshopFileDeleteRequestDto;
import com.shop.myfile.api.dto.request.MyshopSingleFileUploadRequestDto;
import com.shop.myfile.api.dto.response.MyshopFileDeleteResponseDto;
import com.shop.myfile.api.dto.response.MyshopSingleFileUploadResponseDto;
import com.shop.myfile.api.service.MyshopS3Service;
import com.shop.myfile.exception.CustomFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final MyshopS3Service myshopS3Service;

    @PostMapping
    public MyshopSingleFileUploadResponseDto uploadFile(@ModelAttribute MyshopSingleFileUploadRequestDto requestDto)
            throws CustomFileException {
        log.info("Upload file API start.");
        log.info("MyshopSingleFileRequestDto : {}", requestDto);
        return myshopS3Service.upload(requestDto);
    }

    @DeleteMapping
    public MyshopFileDeleteResponseDto deleteFiles(@RequestBody MyshopFileDeleteRequestDto requestDto)
            throws CustomFileException{
        log.info("Delete file API start.");
        log.info("MyshopFileDeleteRequestDto : {}", requestDto);
        return myshopS3Service.deleteFiles(requestDto);
    }
}
