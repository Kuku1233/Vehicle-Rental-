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

        if (!vehicle.isAvailable() || vehicle.getStock() <= 0) {
            throw new IllegalStateException("Vehicle is not available or out of stock");
        }

        double totalCost = (days * vehicle.getPricePerDay()) + (insurance != null ? insurance.getCost() : 0.0);

        // Decrease stock
        vehicle.setStock(vehicle.getStock() - 1);

        // Mark unavailable if stock becomes 0
        if (vehicle.getStock() == 0) {
            vehicle.setAvailable(false);
        }

        vehicleRepository.save(vehicle);

        Booking booking = new Booking(customerUsername, vehicle, insurance, startDate, endDate, totalCost);
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
        if (booking != null && !booking.getReturned()) {
            booking.setReturned(true);

            Vehicle vehicle = booking.getVehicle();
            vehicle.setStock(vehicle.getStock() + 1);
            vehicle.setAvailable(true); // in case it was false due to stock = 0

            vehicleRepository.save(vehicle);
            bookingRepository.save(booking);
        }
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

}
