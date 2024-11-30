package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT " +
           "CASE " +
           "WHEN e.salary BETWEEN 0 AND 50000 THEN '0-50k' " +
           "WHEN e.salary BETWEEN 50001 AND 100000 THEN '50k-100k' " +
           "WHEN e.salary BETWEEN 100001 AND 150000 THEN '100k-150k' " +
           "WHEN e.salary > 150000 THEN '>150k' " +
           "END AS range, " +
           "COUNT(e) AS count " +
           "FROM Employee e " +
           "GROUP BY range")
    List<Object[]> getSalaryRanges();

    @Query("SELECT e.department AS department, COUNT(e) AS count " +
           "FROM Employee e " +
           "GROUP BY e.department")
    List<Object[]> getEmployeeCountByDepartment();

    @Query("SELECT e.department AS department, SUM(e.salary) AS totalSalary " +
       "FROM Employee e " +
       "GROUP BY e.department")
    List<Object[]> getTotalSalaryByDepartment();

}

