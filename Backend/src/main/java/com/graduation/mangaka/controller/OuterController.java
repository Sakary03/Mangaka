package com.graduation.mangaka.controller;

import com.cloudinary.utils.ObjectUtils;
import com.graduation.mangaka.dto.request.CloudinaryDTO;
import com.graduation.mangaka.util.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/outer")
public class OuterController {
    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("/cloudinary/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        CloudinaryDTO res= cloudinaryService.uploadImage(file);
        if (res==null) {
            return ResponseEntity.badRequest().body("File upload failed");
        } else {
            return ResponseEntity.ok().body(res);
        }
    }
}
