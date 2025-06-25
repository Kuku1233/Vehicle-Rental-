package org.vehiclerental.vehiclerentalsystem.service;

import org.vehiclerental.vehiclerentalsystem.model.Insurance;
import org.vehiclerental.vehiclerentalsystem.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    public List<Insurance> getAllInsurances() {
        return insuranceRepository.findAll();
    }

    public void saveInsurance(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    public void deleteInsurance(Long id) {
        insuranceRepository.deleteById(id);
    }
    public Insurance getById(Long id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found"));
    }


}
