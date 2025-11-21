package dev.yuizho.springhtmlx.configs;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dev.yuizho.springhtmlx.file-upload")
public record FileUploadProperties(String bucketName) {
}
