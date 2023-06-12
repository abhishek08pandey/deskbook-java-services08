package com.onerivet.deskbook.services.impl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.models.entity.Employee;
import com.onerivet.deskbook.models.entity.SeatConfiguration;
import com.onerivet.deskbook.models.entity.SeatNumber;
import com.onerivet.deskbook.models.entity.SeatRequest;
import com.onerivet.deskbook.models.payload.BodyDto;
import com.onerivet.deskbook.models.payload.BookingHistoryDto;
import com.onerivet.deskbook.models.payload.EmailDto;
import com.onerivet.deskbook.repository.SeatConfigurationRepo;
import com.onerivet.deskbook.repository.SeatRequestRepo;
import com.onerivet.deskbook.services.BookingHistoryService;
import com.onerivet.deskbook.services.EmailService;

import jakarta.mail.MessagingException;

@Service
public class BookingHistoryServiceImpl implements BookingHistoryService {
	@Autowired
	private SeatConfigurationRepo seatConfigurationRepo;

	@Autowired
	private SeatRequestRepo seatRequestRepo;
	
	 @Autowired
	 private EmailService emailService;

	@Override
	public List<BookingHistoryDto> getBookingRequest(String employeeId, Pageable pageable, Integer requestStatus) {
		List<SeatRequest> seats;

		if (requestStatus == null) {
			seats = seatRequestRepo.getBookingHistoryByEmployee(new Employee(employeeId), pageable);
		} else {
			seats = seatRequestRepo.getBookingHistoryByEmployeeAndRequestStatus(new Employee(employeeId), pageable,
					requestStatus);
		}

		List<SeatNumber> unassignedSeats = seatRequestRepo.findUnAssignedSeat(new Employee(employeeId));

		List<BookingHistoryDto> bookingHistoryDtos = new ArrayList<>();

		for (int i = 0; i < seats.size(); i++) {

			if (unassignedSeats.contains(seats.get(i).getSeat())) {
				System.out.println(seats.get(i).getSeat().getSeatNumber());
				BookingHistoryDto bookingHistoryDto = new BookingHistoryDto();
				bookingHistoryDto.setName("Pooja Parmar");
				bookingHistoryDto.setEmail("pooja.parmar@1rivet.com");
				bookingHistoryDto.setRequestStatus(seats.get(i).getRequestStatus());
				bookingHistoryDto.setFloor(seats.get(i).getSeat().getColumn().getFloor().getId());
				bookingHistoryDto.setDeskNumber(seats.get(i).getSeat().getColumn().getColumnName() + ""
						+ seats.get(i).getSeat().getSeatNumber());
				bookingHistoryDto.setBookingDate(seats.get(i).getBookingDate());
				bookingHistoryDto.setRequestId(seats.get(i).getId());

				bookingHistoryDtos.add(bookingHistoryDto);

			} else {
				SeatConfiguration seatConfiguration = seatConfigurationRepo
						.findBySeatNumberAndDeletedByNull(seats.get(i).getSeat());

				BookingHistoryDto bookingHistoryDto = new BookingHistoryDto();
				bookingHistoryDto.setName(seatConfiguration.getEmployee().getFirstName() + " "
						+ seatConfiguration.getEmployee().getLastName());
				bookingHistoryDto.setDeskNumber(seatConfiguration.getSeatNumber().getColumn().getColumnName() + ""
						+ seatConfiguration.getSeatNumber().getSeatNumber());
				bookingHistoryDto.setRequestId(seats.get(i).getId());
				bookingHistoryDto.setRequestStatus(seats.get(i).getRequestStatus());
				bookingHistoryDto.setRequestedDate(seats.get(i).getCreatedDate());
				bookingHistoryDto.setEmail(seatConfiguration.getEmployee().getEmailId());
				bookingHistoryDto.setBookingDate(seats.get(i).getBookingDate());
				bookingHistoryDto.setFloor(seatConfiguration.getSeatNumber().getColumn().getFloor().getId());

				bookingHistoryDtos.add(bookingHistoryDto);
			}
		}
		return bookingHistoryDtos;
	}

	@Override
	public String cancelBooking(String employeeId, int requestId) throws MessagingException {

		SeatRequest seatRequest = seatRequestRepo.findById(requestId).get();
		seatRequest.setRequestStatus(4);
		seatRequest.setDeletedDate(LocalDateTime.now());
		seatRequestRepo.save(seatRequest);
		
		EmailDto emailDto = new EmailDto();
		emailDto.setTo("abhishekpandey81299@gmail.com");// seat owner
		emailDto.setSubject("Office Seat Cancellation Notification ");
		emailDto.setBody(new BodyDto(seatRequest.getEmployee().getFirstName(), seatRequest.getBookingDate()));
		
		emailService.sendMailCancel(emailDto);
		return "Seat is cancelled..";
	}
}