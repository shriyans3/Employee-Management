package com.example.demo.adminModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin") // matches the table name in your database
public class adminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // matches `auto_increment`
    @Column(name = "adminid") // matches the `adminid` column
    private long adminID;

    @Column(name = "admin_name") // matches the `admin_name` column
    private String adminName;

    @Column(name = "admin_password") // matches the `admin_password` column
    private String adminPassword;

    // Default constructor
    public adminModel() {}

    // Parameterized constructor
    public adminModel(String adminName, String adminPassword) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }

    // Getters and setters
    public long getAdminID() {
        return adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
