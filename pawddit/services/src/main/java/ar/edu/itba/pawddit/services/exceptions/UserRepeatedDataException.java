package ar.edu.itba.pawddit.services.exceptions;

public class UserRepeatedDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1652954643269883985L;
	private RepeatedData repeatedEmail;
	private RepeatedData repeatedUsername;

	public void addRepeatedData(RepeatedData data) {
		if(data.equals(RepeatedData.REPEATED_EMAIL))
			repeatedEmail = data;
		else if(data.equals(RepeatedData.REPEATED_USERNAME))
			repeatedUsername = data;
	}
	
	public boolean isEmailRepeated() {
		if(repeatedEmail != null && repeatedEmail.equals(RepeatedData.REPEATED_EMAIL))
			return true;
		
		return false;
	}
	
	public boolean isUsernameRepeated() {
		if(repeatedUsername != null && repeatedUsername.equals(RepeatedData.REPEATED_USERNAME))
			return true;
		
		return false;
	}
}
