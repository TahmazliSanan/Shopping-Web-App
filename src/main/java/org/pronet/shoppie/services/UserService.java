package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserEntity signUp(UserEntity userEntity, MultipartFile file) throws IOException;
    UserEntity getUserByEmail(String email);
    Boolean existsUserByEmail(String email);
}
