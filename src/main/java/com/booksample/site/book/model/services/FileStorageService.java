package com.booksample.site.book.model.services;

import com.booksample.site.book.model.Book;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.upload.photo-output-path}")
    private String fileUploadPath;

    public String saveFile(
            @Nonnull MultipartFile file,
            @NonNull Integer userId
            ){
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(@Nonnull MultipartFile file, @Nonnull String fileUploadSubPath) {
        final String finalPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalPath);
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                return null;
            }
        }
        final String fileExtn = getfileExtn(file.getOriginalFilename());
        String targetFilePath = finalPath + File.separator + System.currentTimeMillis() + "." + fileExtn;
        Path targetPath = Paths.get(targetFilePath);
        try{
            Files.write(targetPath, file.getBytes());
            return targetFilePath;
        } catch (Exception ex) {

        }
        return null;
    }

    private String getfileExtn(String originalFilename) {
        if (StringUtils.trim(originalFilename).isEmpty())
            return "";
        return originalFilename.substring(originalFilename.lastIndexOf(".")+1).toLowerCase();
    }
}
