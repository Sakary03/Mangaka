package com.graduation.mangaka.util;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.graduation.mangaka.dto.request.CloudinaryDTO;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Map;

@Service
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    @Autowired
    private Cloudinary cloudinary;

    public CloudinaryDTO uploadImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return mapToDTO(uploadResult);
        } catch (IOException e) {
            logger.error("Error while uploading file: {}", e.getMessage());
            return null;
        }
    }

    private CloudinaryDTO mapToDTO(Map<String, Object> uploadResult) {
        return CloudinaryDTO.builder()
                .assetId((String) uploadResult.get("asset_id"))
                .publicId((String) uploadResult.get("public_id"))
                .url((String) uploadResult.get("url"))
                .secureUrl((String) uploadResult.get("secure_url"))
                .format((String) uploadResult.get("format"))
                .resourceType((String) uploadResult.get("resource_type"))
                .width((Integer) uploadResult.get("width"))
                .height((Integer) uploadResult.get("height"))
                .createdAt(uploadResult.get("created_at").toString())
                .build();
    }
}
