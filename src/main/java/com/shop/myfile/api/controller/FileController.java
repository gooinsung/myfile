package com.shop.myfile.api.controller;

import com.shop.myfile.api.dto.request.MyshopSingleFileRequestDto;
import com.shop.myfile.api.dto.response.MyshopSingleFileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    @PostMapping
    public MyshopSingleFileResponseDto uploadFile(@RequestBody MyshopSingleFileRequestDto requestDto) {
        log.info("Upload file API start.");
        log.info("MyshopSingleFileRequestDto : {}", requestDto);
        return MyshopSingleFileResponseDto.builder().build();
    }
}
