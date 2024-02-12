package com.ssafy.polaris.global.s3.service;

import com.ssafy.polaris.global.s3.S3Clients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Clients s3Clients;

    public String uploadFile(Long userId, MultipartFile file) throws IOException {
        return s3Clients.uploadFile(userId, file);
    }
}
