package com.onerivet.deskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.model.entities.ColumnDetails;

public interface ColumnRepo extends JpaRepository<ColumnDetails, Integer> {

}
