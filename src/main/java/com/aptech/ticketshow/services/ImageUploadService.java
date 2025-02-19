package com.aptech.ticketshow.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    String uploadImage(MultipartFile image);
}
