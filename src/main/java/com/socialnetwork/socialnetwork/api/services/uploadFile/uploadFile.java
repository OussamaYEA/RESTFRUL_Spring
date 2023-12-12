package com.socialnetwork.socialnetwork.api.services.uploadFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class uploadFile {

    public static String upload(MultipartFile file, String type, String folder) throws IOException {

        if (checkSize(file.getSize()) && checkType(file.getContentType())){
            Path resourceDirectory = Paths.get("src","main","resources","static" ,"upload" ,type, folder);
            String absolutePath = resourceDirectory.toFile().getAbsolutePath();
            String fileName = java.time.LocalDateTime.now() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(absolutePath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
        return null;
    }

    private static Boolean checkType(String type){
        String[] allowedTypes = new String[]{"image/jpeg", "image/png", "image/jpg"};
        boolean contains = Arrays.stream(allowedTypes).anyMatch(type::equals);
        return contains;
    }

    private static Boolean checkSize(Long size){
        if(size > 10000000)
            return false;
        return true;
    }
}
