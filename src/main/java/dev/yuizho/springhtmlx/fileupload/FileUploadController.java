package dev.yuizho.springhtmlx.fileupload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {
    private final S3Service s3Service;
    private final String bucketName;

    public FileUploadController(
            S3Service s3Service,
            @Value("${cloud.aws.s3.bucket-name:test-bucket}") String bucketName
    ) {
        this.s3Service = s3Service;
        this.bucketName = bucketName;
    }

    @GetMapping
    public String index(Model model) {
        return "fileupload/index";
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        var key = s3Service.upload(bucketName, file);
        model.addAttribute("message", "File uploaded successfully. Key: " + key);
        return "fileupload/index";
    }
}
