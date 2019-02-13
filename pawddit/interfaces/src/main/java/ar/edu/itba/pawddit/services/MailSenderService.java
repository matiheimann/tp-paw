package ar.edu.itba.pawddit.services;

public interface MailSenderService {
	
	public void sendVerificationToken(String from, String to, String subject, String htmlContent);
	
}
