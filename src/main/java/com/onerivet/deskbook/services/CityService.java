package com.onerivet.deskbook.services;

import java.util.List;

import com.onerivet.deskbook.model.payload.CityDto;

public interface CityService {

	public List<CityDto> findAll();
}
