package com.onerivet.deskbook.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onerivet.deskbook.models.payload.AcceptRejectDto;
import com.onerivet.deskbook.models.payload.SeatRequestInformationDto;
import com.onerivet.deskbook.models.response.GenericResponse;
import com.onerivet.deskbook.services.SeatRequestService;

import jakarta.mail.MessagingException;

@ExtendWith(MockitoExtension.class)
class SeatRequestControllerTest {
    @Mock
    private SeatRequestService seatRequestService;
    @InjectMocks
    private SeatRequestController seatRequestController;
    @Test
    public void testRequestSeat() throws MessagingException {
        Principal principal = mock(Principal.class);
        SeatRequestInformationDto seatRequestInformationDto = new SeatRequestInformationDto();
        String expectedResponse = "Your seat request has been submitted";
        when(principal.getName()).thenReturn("037c1ed8-452b-4332-ba77-bbe2286ebf6a");
        when(seatRequestService.requestSeat("037c1ed8-452b-4332-ba77-bbe2286ebf6a", seatRequestInformationDto))
                .thenReturn(expectedResponse);
        System.out.println(expectedResponse);
        GenericResponse<String> actualResponse = seatRequestController.requestSeat(principal,
                seatRequestInformationDto);
        System.out.println(actualResponse);
        assertEquals(expectedResponse, actualResponse.getData(), actualResponse.getError());
    }
    @Test
    void testAcceptReject() throws IOException, MessagingException {
        Principal principal = mock(Principal.class);
        AcceptRejectDto takeAction = new AcceptRejectDto();
        String expectedResponse = "Acceptance or rejection successful";
        when(principal.getName()).thenReturn("037c1ed8-452b-4332-ba77-bbe2286ebf6a");
        when(seatRequestService.acceptReject("037c1ed8-452b-4332-ba77-bbe2286ebf6a", takeAction))
                .thenReturn(expectedResponse);
        System.out.println(expectedResponse);
        GenericResponse<String> actualResponse = seatRequestController.acceptReject(principal, takeAction);
        System.out.println(actualResponse);
        assertEquals(expectedResponse, actualResponse.getData(), actualResponse.getError());
    }
}