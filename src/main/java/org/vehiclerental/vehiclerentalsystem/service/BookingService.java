package org.vehiclerental.vehiclerentalsystem.service;

import org.vehiclerental.vehiclerentalsystem.model.*;
import org.vehiclerental.vehiclerentalsystem.repository.BookingRepository;
import org.vehiclerental.vehiclerentalsystem.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Booking createBooking(String customerUsername, Vehicle vehicle, Insurance insurance,
                                 java.time.LocalDate startDate, java.time.LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (endDate.isBefore(startDate) || endDate.equals(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (!vehicle.isAvailable()) {
            throw new IllegalStateException("Vehicle is not available");
        }
        double totalCost = (days * vehicle.getPricePerDay()) + (insurance != null ? insurance.getCost() : 0.0);

        Booking booking = new Booking(customerUsername, vehicle, insurance, startDate, endDate, totalCost);
        vehicle.setAvailable(false);
        vehicleRepository.save(vehicle);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByCustomer(String username) {
        return bookingRepository.findByCustomerUsername(username);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public void markBookingAsReturned(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setReturned(true);
            booking.getVehicle().setAvailable(true); // make vehicle available again
            bookingRepository.save(booking);
        }
    }

}
