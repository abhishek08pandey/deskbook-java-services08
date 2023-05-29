package com.onerivet.deskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.model.entities.Designation;

public interface DesignationRepo extends JpaRepository<Designation, Integer> {

}
