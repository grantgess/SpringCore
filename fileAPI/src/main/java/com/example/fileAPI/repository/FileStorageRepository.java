package com.example.fileAPI.repository;

import com.example.fileAPI.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;

@Repository
public interface FileStorageRepository extends JpaRepository<FileData, Integer> {
    public FileData findById(int id);
}
