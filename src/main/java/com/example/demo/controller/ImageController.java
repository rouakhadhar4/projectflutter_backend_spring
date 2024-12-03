package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    // Retrieve image by ID
    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageId) {
        Optional<Image> imageOpt = imageRepository.findById(imageId);
        if (imageOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Image image = imageOpt.get();
        if (image.getDonnees() == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(image.getDonnees());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Set the correct content type (change accordingly)
                .body(resource);
    }
}
