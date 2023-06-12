package com.onerivet.deskbook.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.onerivet.deskbook.models.entity.City;
import com.onerivet.deskbook.models.entity.ColumnDetails;
import com.onerivet.deskbook.models.entity.Designation;
import com.onerivet.deskbook.models.entity.Employee;
import com.onerivet.deskbook.models.entity.Floor;
import com.onerivet.deskbook.models.entity.ModeOfWork;
import com.onerivet.deskbook.models.entity.SeatNumber;
import com.onerivet.deskbook.models.entity.SeatRequest;
import com.onerivet.deskbook.models.payload.AcceptRejectDto;
import com.onerivet.deskbook.models.payload.BodyDto;
import com.onerivet.deskbook.models.payload.EmailDto;
import com.onerivet.deskbook.repository.EmployeeRepo;
import com.onerivet.deskbook.repository.SeatConfigurationRepo;
import com.onerivet.deskbook.repository.SeatRequestRepo;
import com.onerivet.deskbook.services.EmailService;

import jakarta.mail.MessagingException;

@ExtendWith(MockitoExtension.class)
class SeatRequestServiceImplTest {

	@InjectMocks
	private SeatRequestServiceImpl serviceImpl;
	@Mock
	private SeatRequestRepo seatRequestRepo;
	@Mock
	private EmployeeRepo employeeRepo;
	@Mock
	private SeatConfigurationRepo seatConfigurationRepo;
	@Mock
	private EmailService emailService;

	@BeforeEach
	void setUp() {
		serviceImpl = new SeatRequestServiceImpl(seatRequestRepo, employeeRepo, seatConfigurationRepo, emailService);
	}

	@Test
	void testAcceptReject() throws MessagingException {

		LocalDate bookingDate = LocalDate.now();
		Designation designation = Designation.builder().id(1).designationName("DEV").build();
		ModeOfWork modeOfWork = ModeOfWork.builder().id(1).modeOfWorkName("Regular").build();

		Employee employee = new Employee();
		employee = Employee.builder().id("ygjvb76-jhgj6-hk6fgh").emailId("xyz@gmail.com").firstName("xyz")
				.lastName("abc").phoneNumber("7778889994").profilePictureFileName("String")
				.profilePictureFilePath("String").modeOfWork(modeOfWork).designation(designation).build();
		int pending = 1;

		City city = City.builder().id(1).cityName("Valsad").build();
		Floor floor = Floor.builder().id(1).floorName("1st Floor").city(city).build();
		ColumnDetails column = ColumnDetails.builder().id(1).columnName("H").floor(floor).build();
		SeatNumber seat = SeatNumber.builder().id(1).seatNumber(1).column(column).build();

		LocalDateTime createdDate = LocalDateTime.now(), modifiedDate = LocalDateTime.now(),
				deletedDate = LocalDateTime.now();

		String reason = "I want to book seat";

		SeatRequest requestedEmployee = new SeatRequest(pending, employee, seat, createdDate, bookingDate, reason,
				pending, modifiedDate, employee, deletedDate);
		
		
		AcceptRejectDto accept = new AcceptRejectDto();
		
		accept.setBookingDate(LocalDate.now());
		accept.setEmailId("xyz@gmail.com");
		accept.setEmployeeId("123");
		accept.setFloor(1);
		accept.setRequestStatus(2);
		accept.setSeatId(1);
		
		when(seatRequestRepo
				.findByRequestStatusAndEmployeeIdAndSeatIdAndBookingDateAndDeletedDateNull(1, accept.getEmployeeId(),
						accept.getSeatId(), accept.getBookingDate())).thenReturn(requestedEmployee);
				
		SeatRequest requestedEmployeeTest = seatRequestRepo
				.findByRequestStatusAndEmployeeIdAndSeatIdAndBookingDateAndDeletedDateNull(1, accept.getEmployeeId(),
						accept.getSeatId(), accept.getBookingDate());
		
		assertThat(requestedEmployee).isEqualTo(requestedEmployeeTest);

		if (accept.getRequestStatus() == 2) {// Approve = 2
			System.out.println("reach upside");
			// Update for approve

			System.out.println("down side");

			requestedEmployee.setRequestStatus(2);
			requestedEmployee.setModifiedBy(employee);
			requestedEmployee.setModifiedDate(LocalDateTime.now());

			SeatRequest save = new SeatRequest(pending, employee, seat, createdDate, bookingDate, reason,
					pending, modifiedDate, employee, deletedDate);
			
			when(seatRequestRepo.save(requestedEmployee)).thenReturn(save);
			
			SeatRequest saveTest = seatRequestRepo.save(requestedEmployee);
	
			assertThat(save).isEqualTo(saveTest);
			System.out.println(save);
			System.out.println(saveTest);

			if (save != null) {
				BodyDto body = new BodyDto();

				body.setEmployeeName(requestedEmployee.getEmployee().getFirstName());
				body.setBookingDate(requestedEmployee.getBookingDate());
				body.setCity(requestedEmployee.getSeat().getColumn().getFloor().getCity().getCityName());
				body.setFloorName(requestedEmployee.getSeat().getColumn().getFloor().getFloorName());
				body.setSeatNumber(requestedEmployee.getSeat().getSeatNumber());
				body.setDuration("Full Day");

				EmailDto emailDto = new EmailDto();

				emailDto.setTo(requestedEmployee.getEmployee().getEmailId());
				emailDto.setSubject("Approval of Your Office Seat in the Deskbook Application System");
				emailDto.setBody(body);

				emailService.sendMailApprove(emailDto);
			}
			List<SeatRequest> rejectSeatList = Arrays.asList(new SeatRequest(1, employee, seat, LocalDateTime.now(),
					bookingDate, "Book seat", 1, LocalDateTime.now(), employee, LocalDateTime.now()));

			when(seatRequestRepo.findByEmployeeIdAndRequestStatusAndBookingDateAndDeletedDateNull(
					accept.getEmployeeId(), 1, accept.getBookingDate())).thenReturn(rejectSeatList);

			List<SeatRequest> rejectSeatListTest = seatRequestRepo
					.findByEmployeeIdAndRequestStatusAndBookingDateAndDeletedDateNull(accept.getEmployeeId(), 1,
							accept.getBookingDate());

			assertThat(rejectSeatListTest).isEqualTo(rejectSeatList);

			System.out.println(rejectSeatListTest);
			System.out.println(rejectSeatList);
			if (rejectSeatList != null) {

				for (int i = 0; i < rejectSeatList.size(); i++) {

					rejectSeatList.get(i).setRequestStatus(3);
					rejectSeatList.get(i).setModifiedBy(employee);
					rejectSeatList.get(i).setModifiedDate(LocalDateTime.now());

					SeatRequest save1 = new SeatRequest(pending, employee, seat, createdDate, bookingDate, reason,
							pending, modifiedDate, employee, deletedDate);

					when(seatRequestRepo.save(rejectSeatList.get(i))).thenReturn(save1);
					SeatRequest saveTest1 = seatRequestRepo.save(rejectSeatList.get(i));

					assertThat(saveTest1).isEqualTo(save1);

					System.out.println(save1);
					System.out.println(saveTest1);
				}
			}

			List<SeatRequest> otherEmployeeRequest = Arrays
					.asList(new SeatRequest(1, employee, seat, LocalDateTime.now(), bookingDate, "Book seat", 1,
							LocalDateTime.now(), employee, LocalDateTime.now()));

			when(seatRequestRepo.getByRequestStatusAndBookingDateAndSeatIdAndDeletedDateNull(1,
					accept.getBookingDate(), accept.getSeatId())).thenReturn(otherEmployeeRequest);

			List<SeatRequest> otherEmployeeRequestTest = seatRequestRepo
					.getByRequestStatusAndBookingDateAndSeatIdAndDeletedDateNull(1, accept.getBookingDate(),
							accept.getSeatId());

			assertThat(otherEmployeeRequest).isEqualTo(otherEmployeeRequestTest);

			System.out.println(otherEmployeeRequest);
			System.out.println(otherEmployeeRequestTest);

			if (otherEmployeeRequest != null) {

				for (int i = 0; i < otherEmployeeRequest.size(); i++) {

					otherEmployeeRequest.get(i).setRequestStatus(3);
					otherEmployeeRequest.get(i).setModifiedBy(employee);
					otherEmployeeRequest.get(i).setModifiedDate(LocalDateTime.now());

					seatRequestRepo.save(otherEmployeeRequest.get(i));
//Call Email service for rejected seat
					EmailDto emailDto = new EmailDto();
					emailDto.setTo("abhishekpandey81299@gmail.com");
					emailDto.setSubject("Deskbook Application - Seat Request Rejection ");
					emailDto.setBody(new BodyDto(otherEmployeeRequest.get(i).getEmployee().getFirstName()));

					emailService.sendMailReject(emailDto);
				}
			}
		}

	}
}
