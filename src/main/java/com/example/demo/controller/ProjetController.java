package com.example.demo.controller;

import com.example.demo.entity.Projet;
import com.example.demo.service.ProjetService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping
    public List<Projet> getAllProjets() {
        return projetService.getAllProjets();
    }

    @GetMapping("/{id}")
    public Projet getProjetById(@PathVariable Long id) {
        return projetService.getProjetById(id);
    }

    @PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet) {
        Projet savedProjet = projetService.saveProjet(projet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjet);
    }


    @PutMapping("/{id}")
    public Projet updateProjet(@PathVariable Long id, @RequestBody Projet projet) {
        projet.setId(id);
        return projetService.saveProjet(projet);
    }

    @DeleteMapping("/{id}")
    public void deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
    }
}
