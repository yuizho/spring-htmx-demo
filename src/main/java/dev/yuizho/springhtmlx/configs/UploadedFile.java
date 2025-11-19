package dev.yuizho.springhtmlx.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dev.yuizho.springhtmx.uploaded-file")
public record UploadedFile(String path) {
}
