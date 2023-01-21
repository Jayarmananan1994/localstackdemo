package com.vjay.localstackdemo.controller;

import com.vjay.localstackdemo.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class FileUploadController {

    private final UploadService uploadService;

    @PostMapping("/api/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String fileUrl = uploadService.uploadFile(file.getInputStream(), filename);
        return ResponseEntity.ok("{\"status\":\"success\", \"url\":\"" + fileUrl + "\"}");
    }
}
