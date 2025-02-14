package com.example.fileAPI.controller;

import com.example.fileAPI.dto.ResponseData;
import com.example.fileAPI.model.FileData;
import com.example.fileAPI.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/download")
public class FileDownloadController {

    private final FileStorageService fileStorageService;
    @Autowired
    public FileDownloadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) throws FileNotFoundException {
        FileData fileData = fileStorageService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileData.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + fileData.getFileName() + "\"")
                .body(new ByteArrayResource(fileData.getContent()));
    }

}
