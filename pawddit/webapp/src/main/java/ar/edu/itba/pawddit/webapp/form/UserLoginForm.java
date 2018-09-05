package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserLoginForm {

	@Email
	private String email;
	
	@Size(min = 6, max = 100)
	private String password;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
