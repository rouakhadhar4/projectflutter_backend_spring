package com.example.demo.service;

import com.example.demo.entity.Departement;

import java.util.List;

public interface DepService {

    List<Departement> getAllDepartements();

    Departement getDepartementById(Long id);

    Departement saveDepartement(Departement departement);

    void deleteDepartement(Long id);
}
