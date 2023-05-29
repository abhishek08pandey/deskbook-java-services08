package com.onerivet.deskbook.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {

	private String id;

	private String emailId;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private ModeOfWorkDto modeOfWorkId;

	private String profilePictureFileName;

	private String profilePictureFilePath;

	private DesignationDto designationId;
}
