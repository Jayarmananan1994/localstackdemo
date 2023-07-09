package com.vjay.localstackdemo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.vjay.localstackdemo.config.AwsConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class UploadService {
    private final AwsConfiguration awsConfig;
    private final AmazonS3 s3Client;
    public String uploadFile(InputStream file, String fileName) {
        s3Client.putObject(awsConfig.getBucketName(), fileName, file, new ObjectMetadata());
        return s3Client.getUrl(awsConfig.getBucketName(), fileName).getPath();

    }
}
