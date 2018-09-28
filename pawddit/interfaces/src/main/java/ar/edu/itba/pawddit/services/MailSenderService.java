package ar.edu.itba.pawddit.services;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

public interface MailSenderService {
	
	public void sendVerificationToken(User user, VerificationToken token);
	
}
