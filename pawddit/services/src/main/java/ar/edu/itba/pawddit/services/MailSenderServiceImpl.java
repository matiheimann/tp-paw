package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

@Service
@Async
public class MailSenderServiceImpl implements MailSenderService {
	
	@Autowired
    private JavaMailSender mailSender;

	@Override
	public void sendVerificationToken(final User user, final VerificationToken token, final String url) {
		String recipientAddress = user.getEmail();
		String subject = "Please Confirm Your Email Address";
		String confirmationUrl = "/registrationConfirm?token=" + token.getToken();
		
		String message = "Hi " + user.getUsername() + "!\r\n\r\n" + 
						 "For security purposes, we need you to verify your email address before continuing on our site.\r\n\r\n";

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + url + confirmationUrl);
		mailSender.send(email);
	}

}
