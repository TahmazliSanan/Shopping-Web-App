package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserEntity signUp(UserEntity userEntity, MultipartFile file) throws IOException;
    Boolean deleteAccount(Long id);
    Boolean deleteProfilePhoto(Long id);
    UserEntity addAdmin(UserEntity userEntity, MultipartFile file) throws IOException;
    UserEntity getUserByEmail(String email);
    UserEntity getUserByToken(String token);
    List<UserEntity> getList(String role);
    Page<UserEntity> getList(Integer pageNumber, Integer pageSize, String role);
    Page<UserEntity> searchAdmin(Integer pageNumber, Integer pageSize, String character);
    Page<UserEntity> searchUser(Integer pageNumber, Integer pageSize, String character);
    UserEntity updateUser(UserEntity userEntity);
    UserEntity updateUserProfile(UserEntity userEntity, MultipartFile file) throws IOException;
    Boolean editAccountStatus(Long id, Boolean status);
    Boolean existsUserByEmail(String email);
    void increaseFailedAttempt(UserEntity userEntity);
    Boolean unlockAccountTimeExpired(UserEntity userEntity);
    void userAccountLock(UserEntity userEntity);
    void updateUserResetToken(String email, String resetToken);
}
