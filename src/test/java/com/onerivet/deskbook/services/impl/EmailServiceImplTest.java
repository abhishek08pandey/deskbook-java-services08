package com.onerivet.deskbook.services.impl;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.onerivet.deskbook.models.payload.BodyDto;
import com.onerivet.deskbook.models.payload.EmailDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

	@InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Test
    public void testSendMailApprove() throws MessagingException {
        // Create the test data
        BodyDto body = new BodyDto();
        // Set the properties of the body

        EmailDto emailDto = new EmailDto();
        emailDto.setTo("abc@gmail.com");
        emailDto.setSubject("Approval of Your Office Seat");
        emailDto.setBody(body);

        Context context = new Context();
        context.setVariable("body", emailDto.getBody());

        String expectedHtmlBody = "htmlbody";
        Mockito.when(templateEngine.process("seatacceptedofemployee", context)).thenReturn(expectedHtmlBody);

        // Capture the argument of the setText method
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // Invoke the method under test
        emailService.sendMailApprove(emailDto);

        // Verify the interactions
        Mockito.verify(javaMailSender).send(Mockito.any(MimeMessage.class));
        Mockito.verify(templateEngine).process("seatacceptedofemployee", context);

        // Capture the argument of the setText method
        Mockito.verify(javaMailSender).send(Mockito.any(MimeMessage.class));
        Mockito.verify(templateEngine).process("seatacceptedofemployee", context);
        Mockito.verify(javaMailSender).send(Mockito.any(MimeMessage.class));
        Mockito.verify(templateEngine).process("seatacceptedofemployee", context);
    }

	@Test
	void testSendMailRequest() {
		fail("Not yet implemented");
	}

	@Test
	void testSendMailReject() {
		fail("Not yet implemented");
	}

	@Test
	void testSendMailCancel() {
		fail("Not yet implemented");
	}

}
