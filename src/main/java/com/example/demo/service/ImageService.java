package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Employe;
import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.EmployeRepository;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private ImageRepository imageRepository;

    public String storeImage(Long employeId, MultipartFile image) {
        try {
            // Vérifiez si l'employé existe
            Employe employe = employeRepository.findById(employeId)
                    .orElseThrow(() -> new RuntimeException("Employe not found"));

            // Créez une nouvelle instance de l'image et remplissez-la avec les données
            Image newImage = new Image();
            newImage.setDonnees(image.getBytes()); // Contenu de l'image en bytes
            newImage.setType(image.getContentType()); // Type MIME de l'image
            newImage.setEmploye(employe);

            // Sauvegardez l'image dans la base de données
            imageRepository.save(newImage);

            return "Image uploaded successfully for employe ID: " + employeId;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}
