package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

@Service
public class MailSenderServiceImpl implements MailSenderService {
	
	@Autowired
    private JavaMailSender mailSender;

	@Override
	public void sendVerificationToken(User user, VerificationToken token) {
		String recipientAddress = user.getEmail();
		String subject = "Registration Confirm";
		String confirmationUrl = "/registrationConfirm?token=" + token.getToken();

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("http://localhost:8080/webapp" + confirmationUrl);
		mailSender.send(email);
	}

}
