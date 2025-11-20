package dev.yuizho.springhtmlx.configs;

import io.awspring.cloud.autoconfigure.s3.S3TransferManagerAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Configuration
public class S3Config {
    @Bean
    ApplicationRunner applicationRunner(
            S3Client s3Client,
            @Value("${cloud.aws.s3.bucket-name:test-bucket}") String bucketName
    ) {
        return args -> {
            try {
                s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
            } catch (NoSuchBucketException e) {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
            }
        };
    }
}
