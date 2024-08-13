package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    UserEntity findByResetToken(String token);
    List<UserEntity> findAllByRole(String role);
    Page<UserEntity> findAllByRole(Pageable pageable, String role);
    @Query("SELECT u FROM UserEntity u WHERE (u.fullName LIKE %:character% OR u.email LIKE %:character%) AND u.role = 'Admin'")
    Page<UserEntity> findByFullNameContainingOrEmailContainingAndRoleAdmin(Pageable pageable, String character);
    @Query("SELECT u FROM UserEntity u WHERE (u.fullName LIKE %:character% OR u.email LIKE %:character%) AND u.role = 'User'")
    Page<UserEntity> findByFullNameContainingOrEmailContainingAndRoleUser(Pageable pageable, String character);
}
