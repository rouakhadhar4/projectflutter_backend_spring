package com.example.demo.controller;




import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Departement;
import com.example.demo.service.DepService;


import java.util.List;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {
    private final DepService departementService;

    public DepartementController(DepService departementService) {
        this.departementService = departementService;
    }

    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.getAllDepartements();
    }

    @GetMapping("/{id}")
    public Departement getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }

    @PostMapping
    public Departement createDepartement(@RequestBody Departement departement) {
        return departementService.saveDepartement(departement);
    }

    @PutMapping("/{id}")
    public Departement updateDepartement(@PathVariable Long id, @RequestBody Departement departement) {
        departement.setId(id);
        return departementService.saveDepartement(departement);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
    }
}
