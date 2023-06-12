package com.onerivet.deskbook.services.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.models.entity.City;
import com.onerivet.deskbook.models.entity.Floor;
import com.onerivet.deskbook.models.entity.SeatConfiguration;
import com.onerivet.deskbook.models.entity.SeatNumber;
import com.onerivet.deskbook.models.entity.SeatRequest;
import com.onerivet.deskbook.models.entity.SeatView;
import com.onerivet.deskbook.models.entity.WorkingDay;
import com.onerivet.deskbook.models.payload.SeatInformationViewDto;
import com.onerivet.deskbook.models.payload.SeatViewDto;
import com.onerivet.deskbook.models.payload.TemporarySeatOwnerDto;
import com.onerivet.deskbook.repository.SeatConfigurationRepo;
import com.onerivet.deskbook.repository.SeatNumberRepo;
import com.onerivet.deskbook.repository.SeatRequestRepo;
import com.onerivet.deskbook.services.SeatViewService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class SeatViewServiceImpl implements SeatViewService {

	@Autowired
	private SeatNumberRepo seatNumberRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SeatRequestRepo seatRequestRepo;

	@Autowired
	private SeatConfigurationRepo seatConfigurationRepo;

	
	
	public SeatViewServiceImpl(SeatNumberRepo seatNumberRepo, ModelMapper modelMapper, SeatRequestRepo seatRequestRepo,
			SeatConfigurationRepo seatConfigurationRepo) {
		super();
		this.seatNumberRepo = seatNumberRepo;
		this.modelMapper = modelMapper;
		this.seatRequestRepo = seatRequestRepo;
		this.seatConfigurationRepo = seatConfigurationRepo;
	}

	@Override
	public SeatInformationViewDto seatInformationById(LocalDate bookingDate, int seatid) throws Exception {
		SeatNumber seatNumber = this.seatNumberRepo.findById(seatid).get();

		SeatConfiguration seatinfo = this.seatConfigurationRepo.findBySeatNumberAndDeletedByNull(seatNumber);
		SeatInformationViewDto seatInformationViewDto = new SeatInformationViewDto();
		seatInformationViewDto
				.setName(seatinfo.getEmployee().getFirstName() + " " + seatinfo.getEmployee().getLastName());
		seatInformationViewDto.setDesignation(seatinfo.getEmployee().getDesignation().getDesignationName());
		seatInformationViewDto.setEmail(seatinfo.getEmployee().getEmailId());
		seatInformationViewDto.setCountOfRequest(
				seatRequestRepo.countFindBySeatAndBookingDateAndDeletedDateNull(seatNumber, bookingDate));


		SeatRequest findByRequestStatus = seatRequestRepo.findByBookingDateAndSeatAndDeletedDateNull(bookingDate,
				seatNumber);

		if (findByRequestStatus == null) {
			return seatInformationViewDto;
		}
		TemporarySeatOwnerDto temp = new TemporarySeatOwnerDto();

		temp.setName(findByRequestStatus.getEmployee().getFirstName() + " "
				+ findByRequestStatus.getEmployee().getLastName());
	
		temp.setEmail(findByRequestStatus.getEmployee().getEmailId());
		temp.setDesignation(findByRequestStatus.getEmployee().getDesignation().getDesignationName());

		seatInformationViewDto.setTemporarySeatOwnerDto(temp);

		return seatInformationViewDto;

	}

	@Override
	public List<SeatViewDto> getSeatView(LocalDate bookingDate, Integer cityId, Integer floorId) {


		DayOfWeek day = bookingDate.getDayOfWeek();
		int dayId = day.getValue();

		List<SeatView> view = this.seatNumberRepo.getViewByDateAndCityAndFloorAndWorkingDay(bookingDate, new City(cityId),
				new Floor(floorId), new WorkingDay(dayId));
		view.stream()
				.filter(seatView -> seatView.getStatus().equals("Booked") || seatView.getStatus().equals("Reserved"))
				.forEach(seatView -> seatView.getSeat().setBooked(true));
		return view.stream().map((seatView) -> this.modelMapper.map(seatView, SeatViewDto.class))
				.collect(Collectors.toList());
	}

}
