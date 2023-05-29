package com.onerivet.deskbook.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.model.payload.DesignationDto;
import com.onerivet.deskbook.repository.DesignationRepo;
import com.onerivet.deskbook.services.DesignationService;

@Service
public class DesignationServiceImpl implements DesignationService {

	@Autowired private DesignationRepo designationRepo;
	@Autowired private ModelMapper maodelMapper;
	
	@Override
	public List<DesignationDto> findAll() {
		return designationRepo.findAll().stream().map((designations)->maodelMapper.map(designations, DesignationDto.class)).collect(Collectors.toList());
	}

}
