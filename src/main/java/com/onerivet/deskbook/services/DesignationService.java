package com.onerivet.deskbook.services;

import java.util.List;

import com.onerivet.deskbook.model.payload.DesignationDto;

public interface DesignationService {

	public List<DesignationDto> findAll();
}
