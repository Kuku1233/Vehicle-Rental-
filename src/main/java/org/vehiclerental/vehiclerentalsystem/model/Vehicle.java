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
    // Sedan, SUV, etc.
    @Column(nullable = false)
    private double pricePerDay;
    @Column(name = "image_path")
    private String imagePath;


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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
