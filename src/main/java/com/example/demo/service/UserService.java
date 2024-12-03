package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.PasswordHasher;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Méthode d'authentification
    public Optional<User> authenticate(String email, String rawPassword) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(hashPassword(rawPassword))) {
            return user;
        }
        return Optional.empty(); // Si l'email ou le mot de passe est incorrect
    }

    // Méthode d'inscription de l'utilisateur
    public User register(User user) {
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use!");
        }
        
        // Hashing du mot de passe avant de le sauvegarder
        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    // Méthode de connexion
    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        // Vérifier si l'utilisateur existe et si le mot de passe est correct
        if (user.isPresent()) {
            if (user.get().getPassword().equals(hashPassword(password))) {
                // Authentification réussie, afficher le message de bienvenue
                return "Welcome " + user.get().getFirstName() + " " + user.get().getLastName();
            } else {
                // Mot de passe incorrect
                return "Incorrect password.";
            }
        } else {
            // Email non trouvé
            return "Email not found.";
        }
    }

    // Méthode pour hasher le mot de passe avec SHA-256
    private String hashPassword(String password) {
        return PasswordHasher.hashPassword(password);
    }
}
