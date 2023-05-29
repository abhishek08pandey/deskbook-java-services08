package com.onerivet.deskbook.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {

	private int id;
	private int seatNumber;
	private ColumnDetailsDto columnId;
}
