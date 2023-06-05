package com.onerivet.deskbook.models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporarySeatOwnerDto {

	//A person who get seat
	private String firstName;
	private String lastName;
	private DesignationDto designation;
	private String emailId;
}
