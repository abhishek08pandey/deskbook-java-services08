package com.onerivet.deskbook.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.onerivet.deskbook.models.payload.EmailDto;
import com.onerivet.deskbook.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Component
public class EmailServiceImpl implements EmailService {

	
	 private final JavaMailSender javaMailSender;
	    private final TemplateEngine templateEngine;

	    @Autowired
	    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
	        this.javaMailSender = javaMailSender;
	        this.templateEngine = templateEngine;
	    }
	    
	@Override
	public void sendMailRequest(EmailDto emailDto) throws  MessagingException {
		
        Context context = new Context();
        context.setVariable("body", emailDto.getBody());

        String htmlBody = templateEngine.process("requesttoseatowner", context);
System.out.println("sendMailRequest: "+ htmlBody);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom("DeskBook.1Rivet@outlook.com");
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(htmlBody, true);

        // Send the email
        javaMailSender.send(message);
	}

	@Override
	public void sendMailApprove(EmailDto emailDto) throws MessagingException {
        Context context = new Context();
        context.setVariable("body", emailDto.getBody());

        String htmlBody = templateEngine.process("seatacceptedofemployee", context);

        System.out.println("sendMailApprove: "+htmlBody);
        
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom("xyz@outlook.com");
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(htmlBody, true);

        // Send the email
        javaMailSender.send(message);
	}

	@Override
	public void sendMailReject(EmailDto emailDto) throws MessagingException {
        Context context = new Context();
        context.setVariable("body", emailDto.getBody());

        String htmlBody = templateEngine.process("seatrejectionmailtoemployee", context);
        System.out.println("sendMailReject: "+htmlBody);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom("DeskBook.1Rivet@outlook.com");
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(htmlBody, true);

        // Send the email
        javaMailSender.send(message);
	}

	@Override
	public void sendMailCancel(EmailDto emailDto) throws MessagingException {
		 // Create a Thymeleaf context and add variables
        Context context = new Context();
        context.setVariable("body", emailDto.getBody());

        // Process the HTML template with Thymeleaf
        String htmlBody = templateEngine.process("seatcancellationmailtoowner", context);
        System.out.println("sendMailCancel: "+htmlBody);
        // Create a MimeMessage and set the content
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom("DeskBook.1Rivet@outlook.com");
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(htmlBody, true);

        // Send the email
        javaMailSender.send(message);
	}
}