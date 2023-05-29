package com.onerivet.deskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.model.entities.City;

public interface CityRepo extends JpaRepository<City, Integer> {

}
