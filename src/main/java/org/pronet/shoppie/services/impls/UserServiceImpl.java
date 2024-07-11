package org.pronet.shoppie.services.impls;

import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.repositories.UserRepository;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity signUp(UserEntity userEntity, MultipartFile file) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        userEntity.setProfileImageName(imageName);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("User");
        UserEntity savedUser = userRepository.save(userEntity);
        if (!ObjectUtils.isEmpty(savedUser)) {
            if (!Objects.requireNonNull(file).isEmpty()) {
                File savedFile = new ClassPathResource("static/").getFile();
                Path path = Paths.get(
                        savedFile.getAbsolutePath() +
                                File.separator +
                                "profile-images" +
                                File.separator +
                                file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            return userEntity;
        }
        return null;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
