package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    UserEntity findByRole(String role);
}
