package com.onerivet.deskbook.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWorkingDayDto {

	private int id;
	private EmployeeDto employeeId;
	private WorkingDayDto workingDayId;
}
