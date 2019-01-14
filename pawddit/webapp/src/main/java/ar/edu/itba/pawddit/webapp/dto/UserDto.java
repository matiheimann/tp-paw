package ar.edu.itba.pawddit.webapp.dto;

import ar.edu.itba.pawddit.model.User;

public class UserDto {
	
	private String username;
	private boolean isAdmin;
	private String imageid;
	private long userid;
	
	public static UserDto fromUser(User user) {
		final UserDto dto = new UserDto();
		if (user != null) {
			dto.username = user.getUsername();
			dto.isAdmin = user.getIsAdmin();
			dto.imageid = user.getImageid();
			dto.userid = user.getUserid();
		}
		return dto;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getImageid() {
		return imageid;
	}

	public void setImageid(String imageid) {
		this.imageid = imageid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}
