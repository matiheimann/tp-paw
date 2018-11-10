package ar.edu.itba.pawddit.services;


import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.slf4j.Logger;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

@Service
@Async
public class MailSenderServiceImpl implements MailSenderService {

	private static final String VERIFICATION_TOKEN_TEMPLATE_NAME = "verificationToken.html";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine htmlTemplateEngine;

	/* **************************************************************** */
	/*  Code based on THYMELEAF                                         */
	/*  https://github.com/thymeleaf/thymeleafexamples-springmail       */
	/* **************************************************************** */

	@Override
	public void sendVerificationToken(final User user, final VerificationToken token, final String url, final Locale locale) {

		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		String confirmationUrl = url + "/registrationConfirm?token=" + token.getToken();	
		ctx.setVariable("username", user.getUsername());
		ctx.setVariable("confirmationUrl", confirmationUrl);
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
	    final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
	    final String htmlContent = this.htmlTemplateEngine.process(VERIFICATION_TOKEN_TEMPLATE_NAME, ctx);
	    try {
	    	message.setSubject(applicationContext.getMessage("mail.subject", null, locale));
			message.setFrom("Pawddit.");
			message.setTo(user.getEmail());
			message.setText(htmlContent, true);
	    } catch (MessagingException e) {
	    	LOGGER.error("Messaging Exception occurred.");
	    }
		mailSender.send(mimeMessage);
	}

}
