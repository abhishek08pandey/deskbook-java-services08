package com.onerivet.deskbook.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.models.entity.SeatRequest;
import com.onerivet.deskbook.models.payload.AcceptRejectDto;
import com.onerivet.deskbook.models.payload.BodyDto;
import com.onerivet.deskbook.models.payload.EmailDto;
import com.onerivet.deskbook.repository.SeatRequestRepo;
import com.onerivet.deskbook.services.EmailService;

import jakarta.mail.MessagingException;

@Service
public class AutoApproveSheduleServiceImpl {

	@Autowired
	private SeatRequestRepo seatRequestRepo;

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 0 0 * * *") // Run at 12 AM (midnight) every day
	// @Scheduled(cron = "0 * * * * *") // every minute
	public void acceptRequestwithin24Hours() throws MessagingException {

		LocalDate bookingDate = LocalDate.now().plusDays(1);

		Sort sort = Sort.by(Sort.Direction.ASC, "CreatedDate");
		List<SeatRequest> pendingSeatRequest = seatRequestRepo
				.findByBookingDateAndRequestStatusAndDeletedDateNull(bookingDate, 1, sort);

		if (!pendingSeatRequest.isEmpty()) {

			AcceptRejectDto takeAction = new AcceptRejectDto();

			takeAction.setSeatId(pendingSeatRequest.get(0).getSeat().getSeatNumber());
			takeAction.setEmployeeId(pendingSeatRequest.get(0).getEmployee().getId());
			takeAction.setBookingDate(pendingSeatRequest.get(0).getBookingDate());
			takeAction.setRequestStatus(2);

			if (takeAction.getRequestStatus() == 2) {// Approve = 2

				// Update for approve
				SeatRequest requestedEmployee = seatRequestRepo
						.findByRequestStatusAndEmployeeIdAndSeatIdAndBookingDateAndDeletedDateNull(1,
								takeAction.getEmployeeId(), takeAction.getSeatId(), takeAction.getBookingDate());

				requestedEmployee.setRequestStatus(2);
				requestedEmployee.setModifiedDate(LocalDateTime.now());
				SeatRequest save = seatRequestRepo.save(requestedEmployee);

// Call Email service for Approved seat
				if (save != null) {
					BodyDto body = new BodyDto();

					body.setEmployeeName(requestedEmployee.getEmployee().getFirstName());
					body.setBookingDate(requestedEmployee.getBookingDate());
					body.setCity(requestedEmployee.getSeat().getColumn().getFloor().getCity().getCityName());
					body.setFloorName(requestedEmployee.getSeat().getColumn().getFloor().getFloorName());
					body.setSeatNumber(requestedEmployee.getSeat().getSeatNumber());
					body.setDuration("Full Day");

					EmailDto emailDto = new EmailDto();

					System.out.println("1st");
					emailDto.setTo("sonaligupta0910200@gmail.com");// takeAction.getEmailId());
					emailDto.setSubject("Approval of Your Office Seat in the Deskbook Application System");
					emailDto.setBody(body);

					System.out.println("2st");
					emailService.sendMailApprove(emailDto);
				}

				List<SeatRequest> rejectSeatList = seatRequestRepo
						.findByEmployeeIdAndRequestStatusAndBookingDateAndDeletedDateNull(takeAction.getEmployeeId(), 1,
								takeAction.getBookingDate());

				if (rejectSeatList != null) {

					for (int i = 0; i < rejectSeatList.size(); i++) {

						rejectSeatList.get(i).setRequestStatus(3);
						// rejectSeatList.get(i).setModifiedBy(new Employee(employeeId));
						rejectSeatList.get(i).setModifiedDate(LocalDateTime.now());

						// Call Email service for rejected seat

						seatRequestRepo.save(rejectSeatList.get(i));
					}
				}

				List<SeatRequest> otherEmployeeRequest = seatRequestRepo
						.getByRequestStatusAndBookingDateAndSeatIdAndDeletedDateNull(1, takeAction.getBookingDate(),
								takeAction.getSeatId());

				if (otherEmployeeRequest != null) {

					for (int i = 0; i < otherEmployeeRequest.size(); i++) {

						otherEmployeeRequest.get(i).setRequestStatus(3);
						// otherEmployeeRequest.get(i).setModifiedBy(new Employee(employeeId));
						otherEmployeeRequest.get(i).setModifiedDate(LocalDateTime.now());

						seatRequestRepo.save(otherEmployeeRequest.get(i));
// Call Email service for rejected seat
						EmailDto emailDto = new EmailDto();
						emailDto.setTo("abhishekpandey81299@gmail.com");
						emailDto.setSubject("Deskbook Application - Seat Request Rejection ");
						emailDto.setBody(new BodyDto(otherEmployeeRequest.get(i).getEmployee().getFirstName()));

						emailService.sendMailReject(emailDto);
					}
				}
				// return "Seat Approved";

			}
		}
	}
}