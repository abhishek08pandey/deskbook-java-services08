package com.onerivet.deskbook.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.models.entity.Employee;
import com.onerivet.deskbook.models.entity.SeatConfiguration;
import com.onerivet.deskbook.models.entity.SeatNumber;
import com.onerivet.deskbook.models.entity.SeatRequest;
import com.onerivet.deskbook.models.payload.SeatOwnerDetailsDto;
import com.onerivet.deskbook.models.payload.SendRequestDto;
import com.onerivet.deskbook.models.payload.TemporarySeatOwnerDto;
import com.onerivet.deskbook.repository.EmployeeRepo;
import com.onerivet.deskbook.repository.SeatConfigurationRepo;
import com.onerivet.deskbook.repository.SeatNumberRepo;
import com.onerivet.deskbook.repository.SeatRequestRepo;
import com.onerivet.deskbook.services.SeatRequestService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class SeatRequestServiceImpl implements SeatRequestService {

	@Autowired
	private SeatConfigurationRepo seatConfigurationRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private SeatRequestRepo seatRequestRepo;
	@Autowired
	private SeatNumberRepo seatNumberRepo;

	@Override
	public SeatOwnerDetailsDto seatDetails(String employeeId, SendRequestDto sendRequestDto) {

		SeatOwnerDetailsDto seatOwner = new SeatOwnerDetailsDto();

		if (sendRequestDto.getSeatType().equalsIgnoreCase("YELLOW")
				|| sendRequestDto.getSeatType().equalsIgnoreCase("UNAVAILABLE")) {
			seatOwner.setMessage("Temporarily unavailable");

			System.out.println("yellow");
			return seatOwner;
		}
		// Note: send variable to front and if request is already present
//		// if user already requested
		SeatRequest userSeatRequestPresent = seatRequestRepo.findByEmployeeIdAndSeatIdAndBookingDateAndDeletedDateNull(
				employeeId, new SeatNumber(sendRequestDto.getSeatId()), sendRequestDto.getBookingDate());

// Check No of request on seat
		int numberOfRequest = seatRequestRepo.countFindBySeatIdAndBookingDateAndDeletedDateNull(
				new SeatNumber(sendRequestDto.getSeatId()), sendRequestDto.getBookingDate());

		if (sendRequestDto.getSeatType().equalsIgnoreCase("GRAY")
				|| sendRequestDto.getSeatType().equalsIgnoreCase("UNASSIGNED")) {

			seatOwner.setCountOfRequest(numberOfRequest);
			seatOwner.setMessage("Available");
			System.out.println("gray");
			return seatOwner;
		}else {
		// Seat Owner details
		SeatConfiguration seatConfiguration = seatConfigurationRepo
				.findBySeatNumberAndDeletedByNull(new SeatNumber(sendRequestDto.getSeatId()));
		
		if(seatConfiguration != null) {
		seatOwner.setFirstName(seatConfiguration.getEmployee().getFirstName());
		seatOwner.setLastName(seatConfiguration.getEmployee().getLastName());
		seatOwner.setDesignation(seatConfiguration.getEmployee().getDesignation().getDesignationName());
		seatOwner.setEmailId(seatConfiguration.getEmployee().getEmailId());
		seatOwner.setCountOfRequest(numberOfRequest);

		if (userSeatRequestPresent != null) {
			seatOwner.setMessage("This have already made request");
			return seatOwner;
		}
		if (sendRequestDto.getSeatType().equalsIgnoreCase("RED")
				|| sendRequestDto.getSeatType().equalsIgnoreCase("RESERVED")) {
			seatOwner.setMessage("This Seat is Reserved");
			return seatOwner;
		}
		if (sendRequestDto.getSeatType().equalsIgnoreCase("GREEN")
				|| sendRequestDto.getSeatType().equalsIgnoreCase("AVAILABLE")) {
			seatOwner.setMessage("This Seat is Available");
			return seatOwner;
		}
// If Request is approve
		SeatRequest seatRequest = seatRequestRepo.findByRequestStatusAndBookingDateAndSeatIdAndDeletedDateNull(1,
				sendRequestDto.getBookingDate(), new SeatNumber(sendRequestDto.getSeatId()));

		if (seatRequest == null) {
			return seatOwner;
		} else if (sendRequestDto.getSeatType().equalsIgnoreCase("BLUE")
				|| sendRequestDto.getSeatType().equalsIgnoreCase("BOOKED")) {
			seatOwner.setMessage("This Seat Already Approved");

			TemporarySeatOwnerDto temp = new TemporarySeatOwnerDto();

			temp.setFirstName(seatRequest.getEmployee().getFirstName());
			temp.setLastName(seatRequest.getEmployee().getLastName());
			temp.setEmailId(seatRequest.getEmployee().getEmailId());
			temp.setDesignation(seatRequest.getEmployee().getDesignation().getDesignationName());

			seatOwner.setTemporarySeatOwner(temp);
			return seatOwner;
		}
		}
		seatOwner.setMessage("This Seat is Availables");
		return seatOwner;
		}
	}

	@Override
	public String seatRequest(String employeeId, SendRequestDto sendRequestDto) {

//Maximum limit for request seats is reached, up to 3 seats can be requested for a Day
// check condition who have that seatNumber already at that date
// check the seat no is not request person's seat
// check conditon total 3 request have that seat with data, seatnumber and employeeid single

		// Request sent by employee and his details
		Employee employee = employeeRepo.findById(employeeId).get();
		// seat object
		SeatNumber seatNumber = seatNumberRepo.findById(sendRequestDto.getSeatId()).get();
		// seat owner info
		SeatConfiguration seatConfiguration = seatConfigurationRepo
				.findBySeatNumberAndDeletedByNull(new SeatNumber(sendRequestDto.getSeatId()));

		// check user have approve one seat and he can not book another seat again
//		int alreadyApproveSeat = seatRequestRepo.findRequestStatusByEmployeeIdAndBookingDateAndDeletedDateNull(employeeId, bookingDate);

		// User can total 3 request on same day
		// int countPerDayEmployeeRequest =
		// seatRequestRepo.countFindByEmployeeIdBookingDateAndDeletedDateNull(employeeId,
		// bookingDate);

		// check condition who have that seatNumber already at that date
//		SeatRequest userSeatRequestPresent = seatRequestRepo
//				.findByEmployeeIdAndSeatIdAndBookingDateAndDeletedDateNull(seandRequestDto);
		// no of request present on One seat
		int numberOfRequestOnSeat = seatRequestRepo.countFindBySeatIdAndBookingDateAndDeletedDateNull(seatNumber,
				sendRequestDto.getBookingDate());

//		if(alreadyApproveSeat == 1) {
//			return "The booking for "+bookingDate+" has already been made!";
//		}
////		else 
////			if(countPerDayEmployeeRequest == 3) {
////			return "You request limit exceed!";
////		}
//		else 
		if (numberOfRequestOnSeat == 3) {
			return "This seat is already requested by Three employee, please choose a different available seat for your booking!";
		} else if (seatConfiguration.getSeatNumber() == seatNumber) {
			return "You Can not Request your Own Seat!";
		}
//		else if (userSeatRequestPresent != null) {
//			return "You already requested this Seat!";
//		}
		else {
// Note:- RequestStatus:- 1 = Accepted, 2 = Cancel, 3 = Pending, 4 = Rejected
			SeatRequest seatRequest = new SeatRequest();

			seatRequest.setEmployee(employee);
			seatRequest.setSeatId(seatNumber);
			// 1st request accept by admin and than we get date of booking date
			seatRequest.setBookingDate(sendRequestDto.getBookingDate());
			seatRequest.setReason(sendRequestDto.getReason());
			seatRequest.setRequestStatus(3);
			seatRequest.setCreatedDate(LocalDateTime.now());

			seatRequest = seatRequestRepo.save(seatRequest);

			return "Your seat request has been submitted!";
		}
	}

}
