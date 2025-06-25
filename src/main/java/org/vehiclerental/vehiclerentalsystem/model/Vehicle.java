package org.vehiclerental.vehiclerentalsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
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

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public double getPricePerDay() { return pricePerDay; }

    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
}
