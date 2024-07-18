package org.pronet.shoppie.services.impls;

import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.repositories.UserRepository;
import org.pronet.shoppie.services.UserService;
import org.pronet.shoppie.utils.AppConstants;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        userEntity.setConfirmPassword(passwordEncoder.encode(userEntity.getConfirmPassword()));
        userEntity.setRole("User");
        userEntity.setIsEnabled(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setFailedAttempt(0);
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
    public UserEntity getUserByToken(String token) {
        return userRepository.findByResetToken(token);
    }

    @Override
    public List<UserEntity> getList(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public Boolean editAccountStatus(Long id, Boolean status) {
        Optional<UserEntity> foundedUser = userRepository.findById(id);
        if (foundedUser.isPresent()) {
            UserEntity user = foundedUser.get();
            user.setIsEnabled(status);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void increaseFailedAttempt(UserEntity userEntity) {
        int countOfFailedAttempt = userEntity.getFailedAttempt() + 1;
        userEntity.setFailedAttempt(countOfFailedAttempt);
        userRepository.save(userEntity);
    }

    @Override
    public Boolean unlockAccountTimeExpired(UserEntity userEntity) {
        long lockTime = userEntity.getLockTime().getTime();
        long unlockDurationTime = lockTime + AppConstants.UNLOCK_DURATION_TIME;
        long currentTime = System.currentTimeMillis();
        if (unlockDurationTime < currentTime) {
            userEntity.setAccountNonLocked(true);
            userEntity.setFailedAttempt(0);
            userEntity.setLockTime(null);
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    @Override
    public void userAccountLock(UserEntity userEntity) {
        userEntity.setAccountNonLocked(false);
        userEntity.setLockTime(new Date());
        userRepository.save(userEntity);
    }

    @Override
    public void updateUserResetToken(String email, String resetToken) {
        UserEntity foundedUser = userRepository.findByEmail(email);
        foundedUser.setResetToken(resetToken);
        userRepository.save(foundedUser);
    }
}
