package ar.edu.itba.pawddit.model;

public class User {
	private String username;
	private String password;
	private String email;
	private Boolean isAdmin;
	private Boolean enabled;
	private long userid;
	
	public User(String username, String password, String email, Boolean isAdmin, Boolean enabled, long userid) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isAdmin = isAdmin;
		this.enabled = enabled;
		this.userid = userid;
	}

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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}
