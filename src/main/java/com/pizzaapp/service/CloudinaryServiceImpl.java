package com.pizzaapp.service;

import com.cloudinary.Cloudinary;
import com.pizzaapp.common.Constants;
import com.pizzaapp.errors.ItemAddFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        try {
            File fileToUpload = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
            multipartFile.transferTo(fileToUpload);

            return cloudinary.uploader().upload(fileToUpload, new HashMap()).get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ItemAddFailureException(Constants.ITEM_ADD_EXCEPTION);
        }
    }
}
