package com.shop.myfile.api.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyshopFileDeleteResponseDto {
    private List<FileDeleteResponseDto> fileDeleteResponseDtoList;
}
