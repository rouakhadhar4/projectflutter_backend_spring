package com.example.demo.service;

import com.example.demo.entity.Departement;
import com.example.demo.entity.Projet;
import com.example.demo.repository.ProjetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepository;
    private final DepService depService;  // Dépendance du service Departement

    public ProjetServiceImpl(ProjetRepository projetRepository, DepService depService) {
        this.projetRepository = projetRepository;
        this.depService = depService;  // Injection du service Departement
    }

    @Override
    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    @Override
    public Projet getProjetById(Long id) {
        return projetRepository.findById(id).orElseThrow(() -> new RuntimeException("Projet non trouvé"));
    }

    @Override
    public Projet saveProjet(Projet projet) {
        // Si un département est passé avec le projet, récupérer le département à partir de l'ID
        if (projet.getDepartement() != null && projet.getDepartement().getId() != null) {
            Departement departement = depService.getDepartementById(projet.getDepartement().getId());
            projet.setDepartement(departement);  // Assigner le département complet au projet
        }
        return projetRepository.save(projet);
    }


    @Override
    public void deleteProjet(Long id) {
        projetRepository.deleteById(id);
    }
}
