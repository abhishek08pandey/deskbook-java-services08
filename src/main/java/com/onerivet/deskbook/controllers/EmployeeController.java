package com.onerivet.deskbook.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onerivet.deskbook.models.payload.EmployeeDto;
import com.onerivet.deskbook.models.payload.ProfileUpdatedOrNot;
import com.onerivet.deskbook.models.payload.ProfileViewDto;
import com.onerivet.deskbook.models.payload.UpdateProfileDto;
import com.onerivet.deskbook.models.response.GenericResponse;
import com.onerivet.deskbook.services.EmployeeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/deskbook/user-profile")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	/**
	 * @purpose: Get all employees
	 * @return: List of employeeDto
	 */
	@GetMapping("/employees")
	public GenericResponse<List<EmployeeDto>> getAll() {
		GenericResponse<List<EmployeeDto>> genericResponse = new GenericResponse<>(this.employeeService.getAllEmployees(), null);
		return genericResponse;
	}

	/**
	 * @purpose: Get employee by id
	 * @param: id
	 * @return: ProfileViewDto
	 */
	@GetMapping("/")
	public GenericResponse<ProfileViewDto> getById(Principal principal) throws Exception {
		GenericResponse<ProfileViewDto> genericResponse = new GenericResponse<>(this.employeeService.getEmployeeById(principal.getName()), null);
		return genericResponse;
	}
	
	/**
	 * @purpose: Update employee by id
	 * @param: updateProfileDto
	 * @return: profileViewDto
	 */
	@PutMapping("/")
	public ResponseEntity<GenericResponse<ProfileViewDto>> updateById(Principal principal, @RequestBody @Valid UpdateProfileDto newEmployee)
			throws Exception {
		GenericResponse<ProfileViewDto> genericResponse = new GenericResponse<>(this.employeeService.updateEmpById(principal.getName(), newEmployee), null);
		return new ResponseEntity<GenericResponse<ProfileViewDto>>(genericResponse, HttpStatus.OK);
	}
	
	/**
	 * @purpose: Check profile updated or not
	 * @param: id
	 * @return: ProfileUpdatedOrNot
	 */
	@GetMapping("/isUpdated")
    public GenericResponse<ProfileUpdatedOrNot> isUpdated(Principal principal) throws Exception {
        GenericResponse<ProfileUpdatedOrNot> genericResponse = new GenericResponse<>(this.employeeService.isProfileUpdated(principal.getName()), null);
        return genericResponse;
    }

}
