package org.vehiclerental.vehiclerentalsystem.controller;

import org.vehiclerental.vehiclerentalsystem.model.*;
import org.vehiclerental.vehiclerentalsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private BookingService bookingService;

    // ✅ Admin Dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin_dashboard"; // maps to admin_dashboard.html
    }

    // ✅ View all vehicles
    @GetMapping("/vehicles")
    public String viewVehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllAvailableVehicles());
        return "admin_vehicles"; // maps to admin_vehicles.html
    }

    // ✅ Show form to add new vehicle
    @GetMapping("/vehicles/add")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "add_vehicle"; // maps to add_vehicle.html
    }

    // ✅ Save new vehicle
    @PostMapping("/vehicles/save")
    public String saveVehicle(@ModelAttribute Vehicle vehicle) {
        vehicle.setAvailable(true);
        vehicleService.saveVehicle(vehicle);
        return "redirect:/admin/vehicles";
    }

    // ✅ Delete vehicle by ID
    @GetMapping("/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/admin/vehicles";
    }

    // ✅ View all insurances
    @GetMapping("/insurances")
    public String viewInsurances(Model model) {
        model.addAttribute("insurances", insuranceService.getAllInsurances());
        return "admin_insurances";
    }

    // ✅ Show form to add insurance
    @GetMapping("/insurances/add")
    public String showAddInsuranceForm(Model model) {
        model.addAttribute("insurance", new Insurance());
        return "add_insurances"; // Must match a template file
    }

    // ✅ Save insurance
    @PostMapping("/insurances/save")
    public String saveInsurance(@ModelAttribute Insurance insurance) {
        insuranceService.saveInsurance(insurance);
        return "redirect:/admin/insurances";
    }

    // ✅ Delete insurance
    @GetMapping("/insurances/delete/{id}")
    public String deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id); // make sure this method exists
        return "redirect:/admin/insurances";
    }

    // ✅ View all bookings
    @GetMapping("/bookings")
    public String viewBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin_bookings"; // maps to admin_bookings.html
    }
}
