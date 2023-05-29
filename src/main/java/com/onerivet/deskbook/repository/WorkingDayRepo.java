package com.onerivet.deskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.model.entities.WorkingDay;

public interface WorkingDayRepo extends JpaRepository<WorkingDay, Integer> {

}
