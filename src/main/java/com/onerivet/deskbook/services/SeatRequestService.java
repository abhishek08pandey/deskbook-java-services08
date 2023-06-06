package com.onerivet.deskbook.services;

import com.onerivet.deskbook.models.payload.SeatOwnerDetailsDto;
import com.onerivet.deskbook.models.payload.SendRequestDto;

public interface SeatRequestService {

	public SeatOwnerDetailsDto seatDetails(String employeeId, SendRequestDto sendRequestDto);
	
	 public String seatRequest(String employeeId, SendRequestDto seandRequestDto);
	
}
