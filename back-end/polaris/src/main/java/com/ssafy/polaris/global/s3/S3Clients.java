package com.ssafy.polaris.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3Clients {

    private final S3Client s3Client;
    private final String bucketName = "polaris-bucket";

    public String uploadFile(MultipartFile file) throws IOException {
        // 파일이 없는 경우 처리
        if(file.isEmpty())
            return "https://polaris-bucket.s3.amazonaws.com/mimo.png";
        String fileName = file.getOriginalFilename();

        // S3 버킷에 파일 업로드
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return generateS3Url(fileName);
    }

    private String generateS3Url(String fileName) {
        // 실제 프로덕션에서는 여기에 사용할 URL을 생성하는 로직을 추가해야 합니다.
        // 일반적으로는 S3 버킷의 기본 URL에 파일명을 덧붙여 사용합니다.
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }
}
