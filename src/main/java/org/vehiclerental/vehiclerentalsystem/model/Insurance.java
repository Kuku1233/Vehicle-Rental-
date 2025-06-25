package org.vehiclerental.vehiclerentalsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String providerName;
    private String description;
    @Column(nullable = false)
    private double cost;

    // Constructors
    public Insurance() {}

    public Insurance(String providerName, String description, double cost) {
        this.providerName = providerName;
        this.description = description;
        this.cost = cost;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getProviderName() { return providerName; }

    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }
}
