package com.shop.myfile.api.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyshopSingleFileUploadResponseDto {
    private Boolean result;
    private String originalFileName;
    private String fileUrl;
    private String message;
}
