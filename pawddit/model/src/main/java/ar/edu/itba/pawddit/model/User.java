package ar.edu.itba.pawddit.model;

public class User {
	private String username;
	private long userid;
	
	public User(String username, long userid) {
		this.username = username;
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}
