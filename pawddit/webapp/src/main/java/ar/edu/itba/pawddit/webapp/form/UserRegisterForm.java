package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Pattern;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import ar.edu.itba.pawddit.webapp.form.annotations.EmailNotRepeated;
import ar.edu.itba.pawddit.webapp.form.annotations.MatchingPasswords;
import ar.edu.itba.pawddit.webapp.form.annotations.UsernameNotRepeated;

@EmailNotRepeated
@UsernameNotRepeated
@MatchingPasswords
public class UserRegisterForm {
	
	@NotEmpty
	@Email
	private String email;
	
	@Size(min = 4, max = 100)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String username;
	
	@Size(min = 6, max = 100)
	private String password;
	
	private String repeatPassword;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepeatPassword() {
		return repeatPassword;
	}
	
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
}