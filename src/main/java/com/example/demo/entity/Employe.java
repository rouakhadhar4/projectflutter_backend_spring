package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields in the JSON response
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String poste;
    private Double salaire;

    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;

 
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;


    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;


    @Transient // To avoid persisting base64 string in the DB
    private String imageBase64Data;

    // Dynamically populate imageBase64Data when requested
    public String getImageBase64Data() {
        if (images != null && !images.isEmpty() && images.get(0).getDonnees() != null) {
            return convertImageToBase64(images.get(0).getDonnees());
        }
        return null; // Return null if no image or image is empty
    }

    // Helper method to convert image to base64
    private String convertImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", poste='" + poste + '\'' +
                ", salaire=" + salaire +
                ", dateEmbauche=" + dateEmbauche +
                '}';
    }
}
