package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"employes"}) // Ignore 'employes' when serializing 'Departement'
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateCreation = new Date();

     // Manages the serialization of 'employes' when serializing 'Departement'


    @JsonManagedReference // Manages the serialization of 'employes' when serializing 'Departement'
    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    private List<Employe> employes;
  
}
