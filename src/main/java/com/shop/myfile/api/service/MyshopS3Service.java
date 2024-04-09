package com.shop.myfile.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.shop.myfile.api.dto.request.MyshopSingleFileUploadRequestDto;
import com.shop.myfile.api.dto.response.MyshopSingleFileUploadResponseDto;
import com.shop.myfile.exception.CustomFileExceptionCode;
import com.shop.myfile.exception.CustomFileUploadException;
import com.shop.myfile.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
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
    public MyshopSingleFileUploadResponseDto upload(MyshopSingleFileUploadRequestDto requestDto) throws CustomFileUploadException {

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
            return this.uploadImageToS3(requestDto.getFile(), requestDto.getBucket());
        } catch (IOException e) {
            throw new S3Exception(CustomFileExceptionCode.FAIL);
        }
    }

    public String uploadImageToS3(MultipartFile file, String bucket) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFileName;

        InputStream is = file.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(bytes.length);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucket, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error("Exception in uploadImageToS3");
            throw new S3Exception(S3_EXCEPTION);
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public void deleteFileFromS3(String fileUrl, String bucket) {
        String key = getFromFileUrl(fileUrl);

        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (Exception e) {
            throw new S3Exception(FILE_DELETE_EXCEPTION);
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
