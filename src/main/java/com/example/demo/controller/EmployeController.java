package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Employe;
import com.example.demo.entity.Projet;
import com.example.demo.service.DepService;
import com.example.demo.service.EmployeService;
import com.example.demo.service.ImageService;
import com.example.demo.service.ProjetService;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final EmployeService employeService;
    private final ImageService imageService;
    private final DepService departementService;
    private final ProjetService projetService;

    @Autowired
    public EmployeController(EmployeService employeService, ImageService imageService, 
                              DepService departementService, ProjetService projetService) {
        this.employeService = employeService;
        this.imageService = imageService;
        this.departementService = departementService;
        this.projetService = projetService;
    }

    // Create a new employee
    @PostMapping
    public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {
        if (employe.getDepartement() == null || employe.getDepartement().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Message d'erreur pour relations manquantes
        }

        Departement departement = departementService.getDepartementById(employe.getDepartement().getId());
        if (departement == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Message d'erreur si le département n'existe pas
        }

        Projet projet = projetService.getProjetById(employe.getProjet().getId());
        if (projet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Message d'erreur si le projet n'existe pas
        }

        employe.setDepartement(departement);
        employe.setProjet(projet);

        Employe createdEmploye = employeService.createEmploye(employe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmploye);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployes() {
        List<Employe> employes = employeService.getAllEmployes();
        return ResponseEntity.ok(employes);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        Employe employe = employeService.getEmployeById(id);
        if (employe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employe);
    }

 // Update an employee
    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
        // Vérifiez si l'employé avec cet ID existe
        Employe existingEmploye = employeService.getEmployeById(id);
        if (existingEmploye == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retourner 404 si l'employé n'existe pas
        }

        // Vérifiez si le département et le projet existent
        Departement departement = departementService.getDepartementById(employe.getDepartement().getId());
        Projet projet = projetService.getProjetById(employe.getProjet().getId());

        if (departement == null || projet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Message d'erreur pour relations manquantes
        }

        // Mettre à jour les informations de l'employé existant
        existingEmploye.setNom(employe.getNom());
        existingEmploye.setPrenom(employe.getPrenom());
        existingEmploye.setEmail(employe.getEmail());
        existingEmploye.setTelephone(employe.getTelephone());
        existingEmploye.setPoste(employe.getPoste());
        existingEmploye.setSalaire(employe.getSalaire());
        existingEmploye.setDateEmbauche(employe.getDateEmbauche());
        existingEmploye.setDepartement(departement);
        existingEmploye.setProjet(projet);

        // Effectuer la mise à jour dans la base de données
        Employe updatedEmploye = employeService.updateEmploye(id, existingEmploye);
        
        // Retourner la réponse avec l'employé mis à jour
        return ResponseEntity.ok(updatedEmploye);
    }


    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        boolean deleted = employeService.deleteEmploye(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

    // Upload an image for an employee
    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        Employe employe = employeService.getEmployeById(id);
        if (employe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

        // Save the image for the employee
        imageService.storeImage(id, image);
        return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully for employee ID: " + id);
    }
}
