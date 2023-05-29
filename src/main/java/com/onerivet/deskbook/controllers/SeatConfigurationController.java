package com.onerivet.deskbook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.deskbook.model.payload.FloorDto;
import com.onerivet.deskbook.services.SeatConfigurationService;

import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/deskbook")
public class SeatConfigurationController {

	@Autowired private SeatConfigurationService seatConfigurationService;
	
	@GetMapping("/floors/{cityId}")
	public List<FloorDto> findAllFloor(@PathVariable("cityId") @Positive(message = "CityId must be positive")  int cityId){
		return seatConfigurationService.findAllFloor(cityId);
	}
}
