package com.onerivet.deskbook.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onerivet.deskbook.models.payload.SeatOwnerDetailsDto;
import com.onerivet.deskbook.models.response.GenericResponse;
import com.onerivet.deskbook.services.SeatRequestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/deskbook")
public class SeatRequestController {
	
	@Autowired private SeatRequestService seatRequestService;
	
	@GetMapping("/seat/{id}")
	public GenericResponse<SeatOwnerDetailsDto> seatDetails(@PathVariable("id") int id) {
		return new GenericResponse<SeatOwnerDetailsDto>(seatRequestService.seatDetails(id), null);
	}
	
	@PostMapping("/request-seat/{seatId}/{reason}")
    public GenericResponse<String> seatRequest(@PathVariable ("seatId") int seatId, @PathVariable ("reason") String reason, Principal principal) {
        return new GenericResponse<String>(seatRequestService.seatRequest(seatId,reason,principal.getName()), null);
    }
	

}
