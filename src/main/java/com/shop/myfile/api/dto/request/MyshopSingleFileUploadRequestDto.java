package com.shop.myfile.api.dto.request;

import com.shop.myfile.exception.CustomFileException;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.shop.myfile.exception.CustomFileExceptionCode.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyshopSingleFileUploadRequestDto {
    private MultipartFile file;
    private String bucket;
    private String message;
    private String fileFolderPath;

    public static void requestValidation(MyshopSingleFileUploadRequestDto requestDto) throws CustomFileException {
        if(requestDto.getFile().isEmpty())
            throw new CustomFileException(FILE_EMPTY_EXCEPTION);
        if(!StringUtils.hasText(requestDto.getBucket()))
            throw new CustomFileException(BUCKET_NAME_EXCEPTION);
    }
}
