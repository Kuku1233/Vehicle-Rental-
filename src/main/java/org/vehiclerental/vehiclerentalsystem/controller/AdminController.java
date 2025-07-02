package org.vehiclerental.vehiclerentalsystem.controller;

import org.vehiclerental.vehiclerentalsystem.model.*;
import org.vehiclerental.vehiclerentalsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private BookingService bookingService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";


    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/vehicles")
    public String viewVehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllAvailableVehicles());
        return "admin_vehicles";
    }

    @GetMapping("/vehicles/add")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "add_vehicle";
    }

    @PostMapping("/vehicles/save")
    public String saveVehicle(@ModelAttribute Vehicle vehicle,
                              @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                // Define upload directory inside static/images
                String uploadDir = "src/main/resources/static/images/";
                File dir = new File(uploadDir);

                // Define full path where file will be saved
                String filePath = uploadDir + imageFile.getOriginalFilename();
                Path path = Paths.get(filePath);

                // Save file to path
                Files.write(path, imageFile.getBytes());

                // Save relative image path in the entity
                vehicle.setImagePath("/images/" + imageFile.getOriginalFilename());
            }

            vehicle.setAvailable(true);
            vehicleService.saveVehicle(vehicle);
            return "redirect:/admin/vehicles";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }


    // Delete vehicle
    @GetMapping("/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/admin/vehicles";
    }

    // View all insurances
    @GetMapping("/insurances")
    public String viewInsurances(Model model) {
        model.addAttribute("insurances", insuranceService.getAllInsurances());
        return "admin_insurances";
    }

    // Add insurance form
    @GetMapping("/insurances/add")
    public String showAddInsuranceForm(Model model) {
        model.addAttribute("insurance", new Insurance());
        return "add_insurances";
    }

    // Save insurance
    @PostMapping("/insurances/save")
    public String saveInsurance(@ModelAttribute Insurance insurance) {
        insuranceService.saveInsurance(insurance);
        return "redirect:/admin/insurances";
    }

    // Delete insurance
    @GetMapping("/insurances/delete/{id}")
    public String deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return "redirect:/admin/insurances";
    }

    // View bookings
    @GetMapping("/bookings")
    public String viewBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin_bookings";
    }
    // Show form to update existing vehicle
    @GetMapping("/vehicles/update/{id}")
    public String showUpdateVehicleForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "update_vehicle";
    }

    // Handle update vehicle submission
    @PostMapping("/vehicles/update")
    public String updateVehicle(@ModelAttribute Vehicle vehicle,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                String uploadDir = "src/main/resources/static/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String filePath = uploadDir + imageFile.getOriginalFilename();
                Path path = Paths.get(filePath);
                Files.write(path, imageFile.getBytes());

                vehicle.setImagePath("/images/" + imageFile.getOriginalFilename());
            }
            vehicle.setAvailable(true);
            vehicleService.saveVehicle(vehicle);
            return "redirect:/admin/vehicles";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

}
