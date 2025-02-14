package com.example.fileAPI.controller;

import com.example.fileAPI.dto.ResponseData;
import com.example.fileAPI.model.FileData;
import com.example.fileAPI.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class FileUploadController {
    private final FileStorageService fileStorageService;
    @Autowired
    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public List<ResponseData> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
        List<FileData> fileData = fileStorageService.save(files);
        return fileData.stream()
                .map(file->new ResponseData(file.getId(),file.getFileName(),
                        file.getFileSize(), file.getContentType(), file.getURL()))
                .toList();
    }

}
