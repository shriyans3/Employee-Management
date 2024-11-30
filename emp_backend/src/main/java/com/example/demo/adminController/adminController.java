package com.example.demo.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.adminModel.adminModel;
import com.example.demo.adminRepository.adminRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class adminController {

    @Autowired
    private adminRepository repo;

    // Admin login endpoint
    @PostMapping("/admin/login")
    public ResponseEntity<String> loginAdmin(@RequestBody adminModel admin) {
        // Fetch admin by name
        adminModel existingAdmin = repo.findByAdminName(admin.getAdminName());

        if (existingAdmin != null && existingAdmin.getAdminPassword().equals(admin.getAdminPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
