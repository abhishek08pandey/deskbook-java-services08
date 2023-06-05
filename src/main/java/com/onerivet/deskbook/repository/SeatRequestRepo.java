package com.onerivet.deskbook.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.deskbook.models.entity.SeatNumber;
import com.onerivet.deskbook.models.entity.SeatRequest;

public interface SeatRequestRepo extends JpaRepository<SeatRequest, Integer> {

	public SeatRequest findByEmployeeIdAndSeatIdAndBookingDateAndDeletedDateNull(String employee, SeatNumber seatId, LocalDate bookingDate);
	
	public int countFindBySeatIdAndBookingDateAndDeletedDateNull(SeatNumber seatId, LocalDate bookingDate);
	
	public SeatRequest findByRequestStatusAndBookingDateAndSeatIdAndDeletedDateNull(int requestStatus, LocalDate bookingDate,  SeatNumber seatId);
}
