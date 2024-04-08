package com.shop.myfile.api.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyshopSingleFileRequestDto {
    private MultipartFile file;
    private String bucket;
    private String message;
}
