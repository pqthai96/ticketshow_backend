//package com.aptech.ticketshow.services.impl;
//
//import com.aptech.ticketshow.services.ImageUploadService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//
//@Service
//public class ImageUploadServiceImpl implements ImageUploadService {
//
//    @Value("${upload.path}")
//    private String uploadPath;
//
//    @Override
//    public String uploadImage(MultipartFile image) {
//        String fileName = generateFileName(image.getOriginalFilename());
//        File dest = new File(uploadPath + File.separator + fileName);
//
//        //image.transferTo(dest);
//        return "aa";
//    }
//
//    private String generateFileName(String originalFilename) {
//        return System.currentTimeMillis() + "_" + originalFilename;
//    }
//}
