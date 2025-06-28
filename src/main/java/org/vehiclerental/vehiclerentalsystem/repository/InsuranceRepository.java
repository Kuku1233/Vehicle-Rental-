package org.vehiclerental.vehiclerentalsystem.repository;

import org.springframework.stereotype.Repository;
import org.vehiclerental.vehiclerentalsystem.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {}
