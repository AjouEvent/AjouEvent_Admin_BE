package com.ajouevent.admin.service.s3;

import org.springframework.stereotype.Component;

@Component
public class S3UploaderImpl implements S3Uploader {
    @Override
    public String uploadFromUrl(String imageUrl) {
        return "AWS S3 Bucket URL"; //URL 넣어야 함.
    }
}
