package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    UserEntity findByResetToken(String token);
    List<UserEntity> findAllByRole(String role);
}
