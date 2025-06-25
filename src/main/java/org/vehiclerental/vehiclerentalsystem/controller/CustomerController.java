package org.vehiclerental.vehiclerentalsystem.controller;

import org.vehiclerental.vehiclerentalsystem.model.Booking;
import org.vehiclerental.vehiclerentalsystem.model.Insurance;
import org.vehiclerental.vehiclerentalsystem.model.Vehicle;
import org.vehiclerental.vehiclerentalsystem.service.BookingService;
import org.vehiclerental.vehiclerentalsystem.service.InsuranceService;
import org.vehiclerental.vehiclerentalsystem.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private BookingService bookingService;

    // ✅ 1. View all available vehicles
    @GetMapping("/vehicles")
    public String viewAvailableVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllAvailableVehicles();
        model.addAttribute("vehicles", vehicles);
        return "customer_vehicles";
    }
    // ✅ 2. Show booking form for a specific vehicle
    @GetMapping("/book/{vehicleId}")
    public String showBookingForm(@PathVariable Long vehicleId, Model model) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        List<Insurance> insurances = insuranceService.getAllInsurances();

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("insurances", insurances);
        return "book_vehicle";
    }

    @GetMapping("/bookings")
    public String viewBookingHistory(Model model, Principal principal) {
        String username = principal.getName();
        List<Booking> bookings = bookingService.getBookingsByCustomer(username);
        model.addAttribute("bookings", bookings);
        return "customer_bookings";
    }

    @PostMapping("/return/{bookingId}")
    public String returnVehicle(@PathVariable Long bookingId) {
        bookingService.markBookingAsReturned(bookingId);
        return "redirect:/customer/bookings?returned=true";
    }


    // ✅ 3. Process booking with date validation
    @PostMapping("/book/process")
    public String processBooking(@RequestParam Long vehicleId,
                                 @RequestParam(required = false) Long insuranceId,
                                 @RequestParam String startDate,
                                 @RequestParam String endDate,
                                 Principal principal,
                                 Model model) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        LocalDate today = LocalDate.now();

        if (start.isBefore(today) || end.isBefore(today) || end.isBefore(start) || end.equals(start)) {
            model.addAttribute("error", "Invalid booking dates. Start and end date must be today or later, and end date must be after start date.");

            // Reload vehicle and insurance lists to show form again
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            List<Insurance> insurances = insuranceService.getAllInsurances();

            model.addAttribute("vehicle", vehicle);
            model.addAttribute("insurances", insurances);

            return "book_vehicle";  // return to booking form with error
        }

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        Insurance insurance = (insuranceId != null) ? insuranceService.getById(insuranceId) : null;

        bookingService.createBooking(principal.getName(), vehicle, insurance, start, end);

        return "redirect:/customer/vehicles?booked=true";
    }
}
