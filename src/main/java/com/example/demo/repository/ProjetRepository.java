package com.example.demo.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Projet;



public interface ProjetRepository extends JpaRepository<Projet, Long> {
	
}
