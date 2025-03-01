package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String uploadAvatarImage(MultipartFile image, String subDirectory) {
        try {
            Resource resource = new FileSystemResource(uploadPath);
            String baseDir = resource.getFile().getAbsolutePath();

            String directoryPath = baseDir + "/images/users/" + subDirectory + "/avatar/";
            File directory = new File(directoryPath);

            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (!dirCreated) {
                    throw new IOException("Failed to create directory: " + directoryPath);
                }
            }

            String fileName = generateFileName(image.getOriginalFilename());
            File dest = new File(directoryPath + File.separator + fileName);


            image.transferTo(dest);

            return "images/users/" + subDirectory + "/avatar/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(String originalFilename) {
        return System.currentTimeMillis() + "_" + originalFilename;
    }
}
