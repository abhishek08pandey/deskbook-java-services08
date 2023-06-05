package com.onerivet.deskbook.services;

import com.onerivet.deskbook.models.payload.SeatOwnerDetailsDto;

public interface SeatRequestService {

	public SeatOwnerDetailsDto seatDetails(int id);
	
	 public String seatRequest(int seatId,String reason,String employeeId);
	
}
