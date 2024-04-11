package com.shop.myfile.api.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDeleteRequestDto {
    private String filePath;
    private String bucket;
    private String originalFileName;
}
