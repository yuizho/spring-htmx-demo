package dev.yuizho.springhtmlx.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Configuration
@Profile("dev")
public class S3Config {
    @Bean
    ApplicationRunner applicationRunner(
            S3Client s3Client,
            FileUploadProperties fileUploadProperties
    ) {
        return args -> {
            try {
                s3Client.headBucket(HeadBucketRequest.builder().bucket(fileUploadProperties.bucketName()).build());
            } catch (NoSuchBucketException e) {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(fileUploadProperties.bucketName()).build());
            }
        };
    }
}
