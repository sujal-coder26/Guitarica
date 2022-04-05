package com.web.guitarapp.helper;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDirectory, String imageName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPathDir = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPathDir)) {
            Files.createDirectories(uploadPathDir);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path imagePath = uploadPathDir.resolve(imageName);
            Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + imageName, ioe);
        }
    }
}
