package com.shop.myfile.api.dto.request;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyshopFileDeleteRequestDto {
    private List<FileDeleteRequestDto> fileDeleteRequestDtoList;
}
