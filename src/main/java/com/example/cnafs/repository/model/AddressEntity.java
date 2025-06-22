package com.example.cnafs.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type",nullable = false)
    private AddressTypeEntity addressType;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<NotificationEntity> notifications = new ArrayList<>();

    @Column(name = "value", nullable = false)
    private String value;
}
