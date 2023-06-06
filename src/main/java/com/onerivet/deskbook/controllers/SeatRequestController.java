package com.onerivet.deskbook.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onerivet.deskbook.models.payload.SeatOwnerDetailsDto;
import com.onerivet.deskbook.models.payload.SendRequestDto;
import com.onerivet.deskbook.models.response.GenericResponse;
import com.onerivet.deskbook.services.SeatRequestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/deskbook")
public class SeatRequestController {
	
	@Autowired private SeatRequestService seatRequestService;
	
	@GetMapping("/seat/")
	public GenericResponse<SeatOwnerDetailsDto> seatDetails(Principal principal, @RequestBody SendRequestDto sendRequestDto) {
		return new GenericResponse<SeatOwnerDetailsDto>(seatRequestService.seatDetails(principal.getName(), sendRequestDto), null);
	}
	
	@PostMapping("/request-seat")
    public GenericResponse<String> seatRequest(Principal principal, @RequestBody SendRequestDto sendRequestDto) {
        return new GenericResponse<String>(seatRequestService.seatRequest(principal.getName(), sendRequestDto), null);
    }
	

}
