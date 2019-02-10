package ar.edu.itba.pawddit.webapp.dto;

public class IsLoggedInDto {

	private boolean isLoggedIn;
	
	public static IsLoggedInDto fromIsLoggedIn(boolean isLoggedIn) {
		final IsLoggedInDto dto = new IsLoggedInDto();
		dto.isLoggedIn = isLoggedIn;
		return dto;
	}

	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
