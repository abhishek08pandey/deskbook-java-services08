package com.onerivet.deskbook.models.payload;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequestInformationDto {
	private String reason;
	
	@JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate requestDateTime;
    private Integer seatId;
}
