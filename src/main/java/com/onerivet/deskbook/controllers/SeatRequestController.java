package com.onerivet.deskbook.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onerivet.deskbook.models.payload.AcceptRejectDto;
import com.onerivet.deskbook.models.payload.SeatRequestInformationDto;
import com.onerivet.deskbook.models.response.GenericResponse;
import com.onerivet.deskbook.services.SeatRequestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;

@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/deskbook/request-seat")
public class SeatRequestController {

    @Autowired
    private SeatRequestService requestService;
    
    @PostMapping("/")
    public GenericResponse<String> requestSeat(Principal principal, @RequestBody SeatRequestInformationDto seatRequestInformationDto) throws MessagingException {
        
             GenericResponse<String> genericResponse=new GenericResponse<>(this.requestService.requestSeat(principal.getName(), seatRequestInformationDto),null);
        return genericResponse;        
        
    }
    
    /**
     * 
     * @param EmployeeId
     * @param AcceptRejectDto
     * @return String
     * @throws MessagingException
     */
    @PutMapping("/accepted-reject")
    public GenericResponse<String> acceptReject(Principal principal,@RequestBody AcceptRejectDto takeAction) throws  MessagingException{
        GenericResponse<String> genericResponse = new GenericResponse<String>(this.requestService.acceptReject(principal.getName(),takeAction), null);
        return genericResponse;
    }
    
}