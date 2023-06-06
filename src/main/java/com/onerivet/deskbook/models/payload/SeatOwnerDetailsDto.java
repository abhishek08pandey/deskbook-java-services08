package com.onerivet.deskbook.models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatOwnerDetailsDto {

	// owner amd book person
	//
	private String firstName;
	private String lastName;
	private String designation;
	private String emailId;
	private int countOfRequest;
	
	private String message;
	//A person who get seat by approving owner
	private TemporarySeatOwnerDto temporarySeatOwner;
	//Red = RESERVED, YELLOW = UNAVAILABLE, BLUE = BOOKED, GRAY = UNASSIGNED, GREEN = AVAILABLE
}
