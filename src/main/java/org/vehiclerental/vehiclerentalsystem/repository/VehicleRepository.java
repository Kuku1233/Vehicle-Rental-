package org.vehiclerental.vehiclerentalsystem.repository;

import org.vehiclerental.vehiclerentalsystem.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByAvailableTrue();
    List<Vehicle> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model);

}
