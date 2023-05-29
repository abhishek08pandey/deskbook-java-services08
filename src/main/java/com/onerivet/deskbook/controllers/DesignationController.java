package com.onerivet.deskbook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.deskbook.model.payload.DesignationDto;
import com.onerivet.deskbook.services.DesignationService;

@RestController
@RequestMapping("/api/deskbook")
public class DesignationController {

	@Autowired private DesignationService designationService;
	
	@GetMapping("/designation")
	public List<DesignationDto> findAll(){
		return designationService.findAll();
	} 
}
