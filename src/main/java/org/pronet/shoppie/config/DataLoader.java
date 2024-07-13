package org.pronet.shoppie.config;

import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        UserEntity admin = userRepository.findByRole("Admin");
        if (admin == null) {
            admin = new UserEntity(
                    1L,
                    "Sanan Tahmazli",
                    "sanan.tahmazli2329@gmail.com",
                    "+123456789",
                    "Baku",
                    "AZE",
                    "1148",
                    "Baku, Azerbaijan",
                    passwordEncoder.encode("AdminOfShoppier2002"),
                    null,
                    "Admin",
                    true
            );
            userRepository.save(admin);
        }
    }
}
