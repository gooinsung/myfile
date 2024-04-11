package com.shop.myfile.api.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDeleteResponseDto {
    private String originalFileName;
    private boolean isDeleted;
}
