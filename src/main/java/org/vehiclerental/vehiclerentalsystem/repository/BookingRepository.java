package org.vehiclerental.vehiclerentalsystem.repository;

import org.vehiclerental.vehiclerentalsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerUsername(String username);

}
