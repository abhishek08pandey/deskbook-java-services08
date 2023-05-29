package com.onerivet.deskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.model.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
