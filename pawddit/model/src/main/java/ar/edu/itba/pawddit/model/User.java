package ar.edu.itba.pawddit.model;

public class User {
	private String username;
	private String password;
	private String email;
	private int score;
	private long userid;
	
	public User(String username, String password, String email, int score, long userid) {
		this.username = username;
		this.password = password;
		this.setEmail(email);
		this.score = score;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}
