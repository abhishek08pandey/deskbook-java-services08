package com.onerivet.deskbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.model.entities.Seat;

public interface SeatRepo extends JpaRepository<Seat, Integer> {

}
