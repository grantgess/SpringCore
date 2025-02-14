package com.example.fileAPI.controller;

import com.example.fileAPI.dto.ResponseData;
import com.example.fileAPI.model.FileData;
import com.example.fileAPI.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final FileStorageService fileStorageService;
    @Autowired
    public FilesController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<ResponseData> listFiles() {
        List<FileData> files = fileStorageService.findAll();
        return files.stream()
                .map(file -> new ResponseData(file.getId(), file.getFileName(), file.getFileSize(),
                        file.getContentType(), file.getURL()))
                .toList();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable("id") int id) {
        fileStorageService.deleteById(id);
        return ResponseEntity.ok("File deleted successfully");
    }
}
