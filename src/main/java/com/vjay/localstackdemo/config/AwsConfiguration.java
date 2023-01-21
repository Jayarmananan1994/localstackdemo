package com.vjay.localstackdemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AwsConfiguration {

    @Value("${application.bucket.name}")
    private String bucketName;

}
