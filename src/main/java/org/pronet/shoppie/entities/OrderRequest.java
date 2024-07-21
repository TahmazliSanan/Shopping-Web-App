package org.pronet.shoppie.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String paymentType;
}
