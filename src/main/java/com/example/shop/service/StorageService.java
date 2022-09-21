package com.example.shop.service;

import static com.example.shop.utils.FileUtils.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    void save(MultipartFile file, Path path) throws IOException;
    Path save(MultipartFile file, FileType fileType) throws IOException;
    void delete(Path path) throws IOException;
}
