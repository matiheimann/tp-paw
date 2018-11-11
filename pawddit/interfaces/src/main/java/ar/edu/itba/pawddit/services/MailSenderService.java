package ar.edu.itba.pawddit.services;

import java.util.Locale;


import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

public interface MailSenderService {
	
	public void sendVerificationToken(User user, VerificationToken token, String url, final Locale locale);
	
}
