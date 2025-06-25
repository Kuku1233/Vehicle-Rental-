package org.vehiclerental.vehiclerentalsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerUsername;
    private String status;
    @Column(name = "returned")
    private Boolean returned = false;

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", nullable = true)
    private Insurance insurance;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private double totalCost;

    // Constructors
    public Booking() {}

    public Booking(String customerUsername, Vehicle vehicle, Insurance insurance,
                   LocalDate startDate, LocalDate endDate, double totalCost) {
        this.customerUsername = customerUsername;
        this.vehicle = vehicle;
        this.insurance = insurance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCustomerUsername() { return customerUsername; }

    public void setCustomerUsername(String customerUsername) { this.customerUsername = customerUsername; }

    public Vehicle getVehicle() { return vehicle; }

    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Insurance getInsurance() { return insurance; }

    public void setInsurance(Insurance insurance) { this.insurance = insurance; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getTotalCost() { return totalCost; }

    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
}
