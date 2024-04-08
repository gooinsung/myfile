package com.shop.myfile.api.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyshopSingleFileResponseDto {
    private Boolean result;
    private String fileUrl;
    private String message;
}
