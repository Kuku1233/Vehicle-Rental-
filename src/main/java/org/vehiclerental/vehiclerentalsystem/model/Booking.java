package org.vehiclerental.vehiclerentalsystem.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerUsername;
    private String status;
    @Column(name = "returned")
    private Boolean returned = false;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "insurance_id", nullable = true)
    private Insurance insurance;

    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private double totalCost;
    public Booking(String customerUsername, Vehicle vehicle, Insurance insurance,
                   LocalDate startDate, LocalDate endDate, double totalCost) {
        this.customerUsername = customerUsername;
        this.vehicle = vehicle;
        this.insurance = insurance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.returned = false;
        this.status = "Booked";
    }

}
