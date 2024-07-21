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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private Product product;
    @OneToOne(cascade = CascadeType.ALL)
    private OrderAddress orderAddress;
    private Date orderDate;
    private Double price;
    private Long quantity;
    private String status;
    private String paymentType;
}
