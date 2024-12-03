package com.example.demo.service;

import com.example.demo.entity.Employe;
import com.example.demo.entity.Image;
import com.example.demo.repository.EmployeRepository;
import com.example.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public EmployeServiceImpl(EmployeRepository employeRepository, ImageRepository imageRepository) {
        this.employeRepository = employeRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Employe createEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll(); // If you need a custom query like "findAllWithProjets", add it in the repository
    }

    @Override
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employe not found"));
    }

    @Override
    public Employe updateEmploye(Long id, Employe employe) {
        Optional<Employe> existingEmployeOpt = employeRepository.findById(id);
        if (existingEmployeOpt.isEmpty()) {
            return null;  // or throw a custom exception if you prefer
        }

        Employe existingEmploye = existingEmployeOpt.get();
        existingEmploye.setNom(employe.getNom());
        existingEmploye.setPrenom(employe.getPrenom());
        existingEmploye.setEmail(employe.getEmail());
        existingEmploye.setTelephone(employe.getTelephone());
        existingEmploye.setPoste(employe.getPoste());
        existingEmploye.setSalaire(employe.getSalaire());
        existingEmploye.setDateEmbauche(employe.getDateEmbauche());
        existingEmploye.setDepartement(employe.getDepartement());
        existingEmploye.setProjet(employe.getProjet());

        return employeRepository.save(existingEmploye);
    }

    @Override
    public boolean deleteEmploye(Long id) {
        if (!employeRepository.existsById(id)) {
            return false;  // If the employee does not exist
        }
        employeRepository.deleteById(id);
        return true;
    }

    @Override
    public void storeImage(Long id, MultipartFile image) {
        try {
            // Get the employee by ID
            Employe employe = employeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employe not found"));

            // Save the image data to the database
            Image img = new Image();
            img.setDonnees(image.getBytes());
            img.setType(image.getContentType());  // Get MIME type of the image
            img.setEmploye(employe);

            // Save the image
            imageRepository.save(img);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}
