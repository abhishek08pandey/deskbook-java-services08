package com.onerivet.deskbook.services;

import com.onerivet.deskbook.models.payload.EmailDto;

public interface EmailService {

	public String sendMailRequest(EmailDto emaiDto);
}
