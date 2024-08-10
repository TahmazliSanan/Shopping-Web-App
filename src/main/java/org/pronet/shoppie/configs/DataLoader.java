package org.pronet.shoppie.configs;

import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<UserEntity> adminList = userRepository.findAllByRole("Admin");
        if (adminList.isEmpty()) {
            UserEntity newAdmin = new UserEntity(
                    1L,
                    "Sanan Tahmazli",
                    "tehmezlisenan11@gmail.com",
                    "+123456789",
                    "Baku",
                    "AZE",
                    "1148",
                    "Baku, Azerbaijan",
                    passwordEncoder.encode("AdminOfShoppie2002"),
                    passwordEncoder.encode("AdminOfShoppie2002"),
                    null,
                    "Admin",
                    true,
                    true,
                    0,
                    null,
                    null
            );
            adminList.add(newAdmin);
        }
        userRepository.saveAll(adminList);
    }
}
