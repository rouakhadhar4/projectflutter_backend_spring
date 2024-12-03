package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Store image as a large object (binary data)
    private byte[] donnees; // Image data (binary format)

    private String type; // Type MIME of the image (e.g., image/jpeg)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id")
    private Employe employe; // Employee the image belongs to
}
