package com.onerivet.deskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.model.entities.EmployeeWorkingDays;


public interface EmployeeWorkingDayRepo extends JpaRepository<EmployeeWorkingDays, Integer> {

}
