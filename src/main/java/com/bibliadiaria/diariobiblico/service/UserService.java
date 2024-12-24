package com.bibliadiaria.diariobiblico.service;

import com.bibliadiaria.diariobiblico.model.User;
import com.bibliadiaria.diariobiblico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já registrado");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(name, email, hashedPassword);
        userRepository.save(user);
    }
}
