package org.vehiclerental.vehiclerentalsystem.repository;

import org.springframework.stereotype.Repository;
import org.vehiclerental.vehiclerentalsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerUsername(String username);

}
