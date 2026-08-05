package com.test.firstproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    public String saveImage(MultipartFile file) {

        validateFile(file);

        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName =
                    file.getOriginalFilename().replace(" ", "_");

            String fileName =
                    UUID.randomUUID() + "-" + originalFileName;

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            log.info("Image uploaded successfully: {}", fileName);

            return fileName;

        } catch (IOException e) {

            log.error("Failed to upload image.", e);

            throw new RuntimeException("Unable to upload image.", e);

        }
    }

    public Resource loadImage(String fileName) {

        try {

            Path filePath = Paths.get(UPLOAD_DIR)
                    .resolve(fileName)
                    .normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("Image not found.");

        } catch (MalformedURLException e) {

            throw new RuntimeException("Unable to load image.", e);

        }
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please select an image.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("Image size must not exceed 5 MB.");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/jpeg")
                        || contentType.equals("image/jpg")
                        || contentType.equals("image/png"))) {

            throw new RuntimeException(
                    "Only JPG, JPEG and PNG images are allowed."
            );
        }
    }
    public void deleteImage(String fileName) {

        try {

            Path filePath = Paths.get(UPLOAD_DIR)
                    .resolve(fileName)
                    .normalize();

            Files.deleteIfExists(filePath);

            log.info("Image deleted successfully: {}", fileName);

        } catch (IOException e) {

            log.error("Failed to delete image: {}", fileName, e);

            throw new RuntimeException("Unable to delete image.", e);

        }
    }
}