package com.onerivet.deskbook.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onerivet.deskbook.model.payload.ModeOfWorkDto;
import com.onerivet.deskbook.repository.ModeOfWorkRepo;
import com.onerivet.deskbook.services.ModeOfWorkService;

@Service
public class ModeOfWorkServiceImpl implements ModeOfWorkService {

	@Autowired private ModeOfWorkRepo modeOfWorkRepo;
	@Autowired private ModelMapper modelMapper;
	
	@Override
	public List<ModeOfWorkDto> findAll() {
		return modeOfWorkRepo.findAll().stream().map((modeOfWork)->modelMapper.map(modeOfWork, ModeOfWorkDto.class)).collect(Collectors.toList());
	}

}
