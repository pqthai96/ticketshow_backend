package com.aptech.ticketshow.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    String uploadAvatarImage(MultipartFile image, String subDirectory);

    String uploadPositionImage(MultipartFile image, String subDirectory);

    String uploadBannerImage(MultipartFile image, String subDirectory);

    String uploadOrganiserAvatarImage(MultipartFile image, String subDirectory);
}
