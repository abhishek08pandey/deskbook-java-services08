package com.onerivet.deskbook.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDetailsDto {

	private int id;
	private char columnName;
	private FloorDto floorId;
}
