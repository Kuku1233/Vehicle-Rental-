package org.vehiclerental.vehiclerentalsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "insurance")
@Getter
@Setter
@NoArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String providerName;
    private String description;
    @Column(nullable = false)
    private double cost;


    public Insurance(String providerName, String description, double cost) {
        this.providerName = providerName;
        this.description = description;
        this.cost = cost;
    }
}
