package ar.edu.itba.pawddit.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
@Async
public class MailSenderServiceImpl implements MailSenderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	/* **************************************************************** */
	/*  Code based on THYMELEAF                                         */
	/*  https://github.com/thymeleaf/thymeleafexamples-springmail       */
	/* **************************************************************** */

	@Override
	public void sendVerificationToken(final String from, final String to, final String subject, final String htmlContent) {

		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
	    final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
	    try {
	    	message.setSubject(subject);
			message.setFrom(from);
			message.setTo(to);
			message.setText(htmlContent, true);
	    } catch (MessagingException e) {
	    	LOGGER.error("Messaging Exception occurred.");
	    }
		mailSender.send(mimeMessage);
	}

}
