package com.example.shop.service.impl;

import com.example.shop.exception.UnsupportedFileTypeException;
import com.example.shop.service.StorageService;
import com.example.shop.utils.FileUtils;
import static com.example.shop.utils.FileUtils.FileType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class StorageServiceImpl implements StorageService {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void save(MultipartFile file, Path path) throws IOException {
        logger.info("Saving " + path.toString());
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            logger.info("Couldn't save " + path.getFileName().toString());
            throw e;
        }
    }

    @Override
    public Path save(MultipartFile file, FileType fileType) throws IOException {
        // check if file is in supported format
        String fileExtension = FileUtils.getExtension(file.getOriginalFilename());
        if (!fileType.supports(file.getOriginalFilename()))
            throw new UnsupportedFileTypeException("Required " + fileType.getSupportedExtensions() + ", Provided: " + fileExtension);

        // generate secure random, unique name for file
        Path filePath;
        do {
            String fileName = FileUtils.generateUID() + fileExtension;
            filePath = Paths.get(fileType.getDirectory().toString(), fileName);
        } while (Files.exists(filePath));

        // save file
        save(file, filePath);

        return filePath;
    }

    @Override
    public void delete(Path path) throws IOException {
        logger.info("Deleting " + path.toString());
        try {
            Files.delete(path);
        } catch (IOException e) {
            logger.info("Couldn't delete " + path.getFileName().toString());
            throw e;
        }
    }
}
