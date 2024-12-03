package com.example.demo.service;

import com.example.demo.entity.Employe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeService {

    // Create a new employee
    Employe createEmploye(Employe employe);

    // Get all employees
    List<Employe> getAllEmployes();

    // Get an employee by ID
    Employe getEmployeById(Long id);

    // Update an employee
    Employe updateEmploye(Long id, Employe employe);

    // Delete an employee
    boolean deleteEmploye(Long id);

    // Save an image for an employee
    void storeImage(Long id, MultipartFile image);
}
