package com.onerivet.deskbook.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.model.entities.City;
import com.onerivet.deskbook.model.entities.Floor;
import com.onerivet.deskbook.model.payload.FloorDto;
import com.onerivet.deskbook.repository.FloorRepo;
import com.onerivet.deskbook.services.SeatConfigurationService;

@Service
public class SeatConfigurationServiceImpl implements SeatConfigurationService {

	@Autowired
	private FloorRepo floorRepo;
	private ModelMapper modelMapper;

	@Override
	public List<FloorDto> findAllFloor(int cityId) {
		
		List<Floor> floors = this.floorRepo.findByCity(new City(cityId));
//		if(floors.isEmpty()) {
//			System.out.println("empty");
//			//throw new ResourceNotFoundException("City does not exist!");
//		}
		System.out.println(floors);
		return floors.stream().map((floor) -> this.modelMapper.map(floor, FloorDto.class))
				.collect(Collectors.toList());
	}
}
