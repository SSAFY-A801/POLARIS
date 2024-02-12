package com.ssafy.polaris.global.s3.service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    public String uploadFile(Long userId, MultipartFile file) throws IOException;
}
