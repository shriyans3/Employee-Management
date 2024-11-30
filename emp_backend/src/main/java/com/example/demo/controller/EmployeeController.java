package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all data
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees")
	public List <Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}  
	
	
	
	//create 
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}
	
	
	// get data by id 
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getByID(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee with id "+id+"does not exists"));
		return ResponseEntity.ok(employee);
	}
	
	
	//update data 
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping ("/employees/{id}")
	public ResponseEntity<Employee> updateEmployeeByID(@PathVariable Long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee with id "+id+"does not exists"));
		
		
		employee.setFname(employeeDetails.getFname());
		employee.setLname(employeeDetails.getLname());
		employee.setEmail(employeeDetails.getEmail());
		employee.setDepartment(employeeDetails.getDepartment());
		employee.setDesignation(employeeDetails.getDesignation());
		employee.setJoiningDate(employeeDetails.getJoiningDate());
		employee.setSalary(employeeDetails.getSalary());
		
		Employee updatedEmployee=employeeRepository.save(employee);
		
		return ResponseEntity.ok(updatedEmployee);
}
	
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity <Map<String, Boolean> >deleteEmployee(@PathVariable Long id){
		
		
		Employee employee = employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee with id "+id+"does not exists"));
		
		employeeRepository.delete(employee);
		
		Map<String, Boolean>  response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	
}

    
    @GetMapping("/analytics")
    public Map<String, Object> getEmployeeAnalytics() {
        Map<String, Object> analytics = new HashMap<>();

        // Total number of employees
        long totalEmployees = employeeRepository.count();
        analytics.put("totalEmployees", totalEmployees);
		// System.out.println("Total_Employeess"+totalEmployees);

        // Salary ranges
        List<Object[]> salaryRanges = employeeRepository.getSalaryRanges();
        analytics.put("salaryRanges", salaryRanges);

        return analytics;
    }

	@GetMapping("/department-analytics")
    public List<Map<String, Object>> getEmployeeCountByDepartment() {
        List<Object[]> results = employeeRepository.getEmployeeCountByDepartment();
        List<Map<String, Object>> analytics = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("department", result[0]); // Department name
            map.put("count", result[1]); // Employee count
            analytics.add(map);
        }

        return analytics;
    }
	@GetMapping("/department-salary")
	public List<Map<String, Object>> getTotalSalaryByDepartment() {
		List<Object[]> results = employeeRepository.getTotalSalaryByDepartment();
		List<Map<String, Object>> analytics = new ArrayList<>();

		for (Object[] result : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("department", result[0]); // Department name
			map.put("totalSalary", result[1]); // Total salary
			analytics.add(map);
		}

		return analytics;
	}
	
	
}