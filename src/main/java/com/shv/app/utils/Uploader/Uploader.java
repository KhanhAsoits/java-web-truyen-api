package com.shv.app.utils.Uploader;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;


public class Uploader {

    private final String UPLOAD_DIR_PATH = "src/main/resources/upload/";

    public String Upload(MultipartFile file) throws Exception{
        try {
            Path dirPath = Paths.get(this.UPLOAD_DIR_PATH);

            if (!Files.exists(dirPath)){
                Files.createDirectories(dirPath);
            }
            Path filePath = dirPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
            return this.UPLOAD_DIR_PATH+file.getOriginalFilename();
        }catch (Exception exception){
            throw exception;
        }
    }
    public String CreateBase64FromImage(String filePath) {
        try {
            Path file = Paths.get(filePath);
            if (Files.exists(file)){
                byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
                return Base64.getEncoder().encodeToString(fileContent);
            }
            return null;
        }catch (Exception e){
            return null;
        }

    }
}
