package com.graduation.mangaka.util;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String UploadImage() {

    }
}
