package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserEntity signUp(UserEntity userEntity, MultipartFile file) throws IOException;
    UserEntity getUserByEmail(String email);
    List<UserEntity> getList(String role);
    Boolean editAccountStatus(Long id, Boolean status);
    Boolean existsUserByEmail(String email);
    void increaseFailedAttempt(UserEntity userEntity);
    Boolean unlockAccountTimeExpired(UserEntity userEntity);
    void userAccountLock(UserEntity userEntity);
    void resetAttempt(Long id);
}
