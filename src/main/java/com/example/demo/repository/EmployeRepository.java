package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Employe;



public interface EmployeRepository extends JpaRepository<Employe, Long> {
	 // Assurez-vous que cette méthode existe dans le repository avec la bonne signature
    @Query("SELECT e FROM Employe e LEFT JOIN FETCH e.projet")
    List<Employe> findAllWithProjets();

}

