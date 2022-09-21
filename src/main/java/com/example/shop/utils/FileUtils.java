package com.example.shop.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUtils {
    public enum FileType {
        IMAGE ("images",".jpg", ".jpeg", ".png");

        private static final String rootDirectory = "./src/main/webapp";
        private final Set<String> supportedExtensions;
        private final Path directory;

        FileType(String directory, String... supportedExtensions) {
            this.supportedExtensions = new HashSet<>();
            this.supportedExtensions.addAll(Arrays.asList(supportedExtensions));

            this.directory = Paths.get(rootDirectory, directory);
        }

        public Set<String> getSupportedExtensions() {
            return Collections.unmodifiableSet(supportedExtensions);
        }

        public Path getDirectory() {
            return Paths.get(directory.toString());
        }

        public boolean supports(String fileName) {
            String extension = getExtension(fileName);
            return this.supportedExtensions.contains(extension);
        }
    };
    public static String getExtension(String fileName) {
        int beginIndex = fileName.lastIndexOf(".");
        if (beginIndex == -1) {
            return "";
        } else {
            return fileName.substring(beginIndex);
        }
    }
    public static String generateUID() {
        return UUID.randomUUID().toString();
    }
}
