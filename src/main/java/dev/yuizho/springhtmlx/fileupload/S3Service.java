package dev.yuizho.springhtmlx.fileupload;

import io.awspring.cloud.s3.S3Template;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private final S3Template s3Template;

    public S3Service(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    public String upload(String bucketName, MultipartFile file) throws IOException {
        var key = UUID.randomUUID().toString() + "/" + file.getOriginalFilename();
        s3Template.upload(bucketName, key, file.getInputStream());
        return key;
    }
}
