package org.pronet.shoppie.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String city;
    private String state;
    private String pinCode;
    private String address;
    private String password;
    private String profileImageName;
    private String role;
    private Boolean isEnabled;
    private Boolean accountNonLocked;
    private Integer failedAttempt;
    private Date lockTime;
    private String resetToken;
}
