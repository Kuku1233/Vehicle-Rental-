package org.vehiclerental.vehiclerentalsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private double pricePerDay;
    @Column(name = "image_path")
    private String imagePath;
    @Column
    private int stock;


    private boolean available = true;

    // Constructors
    public Vehicle() {}

    public Vehicle(String brand, String model, String type, double pricePerDay) {
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

}
