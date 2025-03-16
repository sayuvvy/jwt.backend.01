package com.booksample.site.book.model.utils;

import io.micrometer.common.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static byte[] readFromLocation(String fileUrl) {
     if (StringUtils.isBlank(fileUrl)){
         return null;
     }
    try {
        Path filePath = new File(fileUrl).toPath();
        return Files.readAllBytes(filePath);
    } catch(Exception ex) {

    }
    return null;
    }
}
