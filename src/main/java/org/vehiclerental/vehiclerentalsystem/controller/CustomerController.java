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

    @GetMapping("/vehicles")
    public String viewAvailableVehicles(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Vehicle> vehicles;
        if (keyword != null && !keyword.trim().isEmpty()) {
            vehicles = vehicleService.searchVehiclesByBrandOrModel(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            vehicles = vehicleService.getAllAvailableVehicles();
        }
        model.addAttribute("vehicles", vehicles);
        return "customer_vehicles";
    }
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

            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            List<Insurance> insurances = insuranceService.getAllInsurances();

            model.addAttribute("vehicle", vehicle);
            model.addAttribute("insurances", insurances);

            return "book_vehicle";
        }

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        Insurance insurance = (insuranceId != null) ? insuranceService.getById(insuranceId) : null;

        Booking booking = bookingService.createBooking(principal.getName(), vehicle, insurance, start, end);

        return "redirect:/customer/payment?bookingId=" + booking.getId();
    }

    // Payment Page
    @GetMapping("/payment")
    public String showPaymentPage(@RequestParam Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("totalAmount", booking.getTotalCost());
        model.addAttribute("notPaid", true);
        return "payment";
    }

    //Process Payment
    @PostMapping("/payment/process")
    public String processPayment(@RequestParam Long bookingId,
                                 @RequestParam String cardNumber,
                                 @RequestParam String expiry,
                                 @RequestParam String pin,
                                 Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        booking.setStatus("PAID");
        bookingService.saveBooking(booking);

        model.addAttribute("paid", true);
        return "payment";
    }
}
