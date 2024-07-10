package org.pronet.shoppie.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public UserEntity(Long id, String fullName, String email, String mobileNumber,
                      String city, String state, String pinCode, String address, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.address = address;
        this.password = password;
    }
}
