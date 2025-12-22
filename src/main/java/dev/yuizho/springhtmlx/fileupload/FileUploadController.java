package dev.yuizho.springhtmlx.fileupload;

import dev.yuizho.springhtmlx.configs.FileUploadProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.io.IOException;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {
    private final S3Service s3Service;
    private final FileUploadProperties fileUploadProperties;

    public FileUploadController(
            S3Service s3Service,
            FileUploadProperties fileUploadProperties
    ) {
        this.s3Service = s3Service;
        this.fileUploadProperties = fileUploadProperties;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("uploaded_files", s3Service.listObjects(fileUploadProperties.bucketName()));

        return "fileupload/index";
    }

    @PostMapping
    public View upload(@RequestParam("file") MultipartFile file, Model model) throws IOException, InterruptedException {
        Thread.sleep(1000);

        var key = s3Service.upload(fileUploadProperties.bucketName(), file);
        model.addAttribute("message", "File uploaded successfully. Key: " + key);
        model.addAttribute("uploaded_files", key);

        return FragmentsRendering
                .with("fileupload/index :: message")
                .fragment("fileupload/index :: uploaded-file")
                .build();
    }
}
