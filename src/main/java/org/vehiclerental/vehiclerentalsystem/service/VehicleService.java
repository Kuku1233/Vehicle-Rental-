package org.vehiclerental.vehiclerentalsystem.service;

import org.vehiclerental.vehiclerentalsystem.model.Vehicle;
import org.vehiclerental.vehiclerentalsystem.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllAvailableVehicles() {
        return vehicleRepository.findByAvailableTrue();
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> searchVehiclesByBrandOrModel(String keyword) {
        return vehicleRepository.findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword);
    }



}
