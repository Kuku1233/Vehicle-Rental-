package org.vehiclerental.vehiclerentalsystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vehiclerental.vehiclerentalsystem.model.User;
import org.vehiclerental.vehiclerentalsystem.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminUsername = "admin";
        String adminPassword = "admin123";
        String adminRole = "ROLE_ADMIN";

        if (userRepository.findByUsername(adminUsername) == null) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(adminRole);
            userRepository.save(admin);
            System.out.println("✅ Admin user created: " + adminUsername);
        } else {
            System.out.println("ℹ️ Admin user already exists.");
        }
    }
}
