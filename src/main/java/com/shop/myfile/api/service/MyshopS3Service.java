package com.shop.myfile.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.shop.myfile.api.dto.request.FileDeleteRequestDto;
import com.shop.myfile.api.dto.request.MyshopFileDeleteRequestDto;
import com.shop.myfile.api.dto.request.MyshopSingleFileUploadRequestDto;
import com.shop.myfile.api.dto.response.FileDeleteResponseDto;
import com.shop.myfile.api.dto.response.MyshopFileDeleteResponseDto;
import com.shop.myfile.api.dto.response.MyshopSingleFileUploadResponseDto;
import com.shop.myfile.exception.CustomFileException;
import com.shop.myfile.exception.CustomFileExceptionCode;
import com.shop.myfile.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.shop.myfile.exception.CustomFileExceptionCode.FILE_DELETE_EXCEPTION;
import static com.shop.myfile.exception.CustomFileExceptionCode.S3_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyshopS3Service {

    private final AmazonS3 amazonS3;

    /**
     * 하나의 파일 업로드를 처리하는 메서드
     *
     * @param requestDto
     */
    public MyshopSingleFileUploadResponseDto upload(MyshopSingleFileUploadRequestDto requestDto) throws CustomFileException {

        MyshopSingleFileUploadRequestDto.requestValidation(requestDto);

        return MyshopSingleFileUploadResponseDto.builder()
                .message(requestDto.getMessage())
                .result(Boolean.TRUE)
                .originalFileName(requestDto.getFile().getOriginalFilename())
                .fileUrl(this.uploadImage(requestDto))
                .build();
    }

    public String uploadImage(MyshopSingleFileUploadRequestDto requestDto) {
        try {
            return this.uploadImageToS3(requestDto);
        } catch (IOException e) {
            throw new S3Exception(CustomFileExceptionCode.FAIL);
        }
    }

    public String uploadImageToS3(MyshopSingleFileUploadRequestDto requestDto) throws IOException {
        String originalFileName = requestDto.getFile().getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String s3FileName = (requestDto.getFileFolderPath() != null ?
                (requestDto.getFileFolderPath() + "/") :
                "") + (UUID.randomUUID().toString().substring(0, 10) + originalFileName);

        InputStream is = requestDto.getFile().getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(bytes.length);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(requestDto.getBucket(), s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error("Exception in uploadImageToS3");
            throw new S3Exception(S3_EXCEPTION);
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(requestDto.getBucket(), s3FileName).toString();
    }

    public MyshopFileDeleteResponseDto deleteFiles(MyshopFileDeleteRequestDto requestDto) {
        List<FileDeleteResponseDto> result = new ArrayList<>();
        requestDto.getFileDeleteRequestDtoList().forEach(x-> result.add(deleteFile(x)));
        return MyshopFileDeleteResponseDto
                .builder()
                .fileDeleteResponseDtoList(result)
                .build();
    }

    public FileDeleteResponseDto deleteFile(FileDeleteRequestDto request){
        return this.deleteFileFromS3(request);
    }

    public FileDeleteResponseDto deleteFileFromS3(FileDeleteRequestDto request) {
        String key = getFromFileUrl(request.getFilePath());

        try {
            amazonS3.deleteObject(new DeleteObjectRequest(request.getBucket(), key));
            return FileDeleteResponseDto
                    .builder()
                    .originalFileName(request.getOriginalFileName())
                    .isDeleted(Boolean.TRUE)
                    .build();
        } catch (Exception e) {
            log.error("Error occur in deleteFileFromS3");
            log.error("Url : {}", key);
            return FileDeleteResponseDto
                    .builder()
                    .originalFileName(request.getOriginalFileName())
                    .isDeleted(Boolean.FALSE)
                    .build();
        }
    }

    public String getFromFileUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new S3Exception(FILE_DELETE_EXCEPTION);
        }
    }
}
