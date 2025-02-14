package com.example.fileAPI.service;

import com.example.fileAPI.model.FileData;
import com.example.fileAPI.repository.FileStorageRepository;
import org.apache.catalina.webresources.FileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileStorageService {
    private final FileStorageRepository fileStorageRepository;
    @Autowired
    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }
    public List<FileData> save(MultipartFile[] files) throws IOException {
        List<FileData> list = new ArrayList<>();
        for (MultipartFile file : files) {
            FileData fileData = new FileData();
            fileData.setFileSize(file.getSize());
            fileData.setFileName(file.getOriginalFilename());
            fileData.setContent(file.getBytes());
            fileData.setContentType(file.getContentType());
            fileStorageRepository.save(fileData);
            fileData.setURL(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(String.valueOf(fileData.getId()))
                    .toUriString());
            list.add(fileData);
        }

        return list;
    }
    public FileData findById(int id){
        return fileStorageRepository.findById(id);
    }
    public List<FileData> findAll(){
        return fileStorageRepository.findAll();
    }
    public void deleteById(int id){
        fileStorageRepository.deleteById(id);
    }

}
