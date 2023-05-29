package com.onerivet.deskbook.services;

import java.util.List;

import com.onerivet.deskbook.model.payload.FloorDto;

public interface SeatConfigurationService {

	public List<FloorDto> findAllFloor(int cityId);
}
