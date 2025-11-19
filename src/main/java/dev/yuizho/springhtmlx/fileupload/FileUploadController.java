package dev.yuizho.springhtmlx.fileupload;

import dev.yuizho.springhtmlx.configs.UploadedFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {
    private final UploadedFile uploadedFile;

    public FileUploadController(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    @GetMapping
    public String index(Model model) {
        System.out.println(uploadedFile.path());
        return "fileupload/index";
    }
}
