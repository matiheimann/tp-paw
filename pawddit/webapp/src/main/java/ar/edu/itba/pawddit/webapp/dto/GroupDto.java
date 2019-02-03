package ar.edu.itba.pawddit.webapp.dto;

import java.time.LocalDateTime;

import ar.edu.itba.pawddit.model.Group;

public class GroupDto {
	
	private String name;
	private LocalDateTime date;
	private String description;
	private UserDto owner;
	private int suscriptors;
	private boolean userSub;
	
	public static GroupDto fromGroup(Group group) {
		if (group == null)
			return null;
		
		final GroupDto dto = new GroupDto();
		dto.name = group.getName();
		dto.date = group.getDate();
		dto.description = group.getDescription();
		dto.owner = UserDto.fromUser(group.getOwner());
		dto.suscriptors = group.getSuscriptors();
		dto.userSub = group.getUserSub();
		return dto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
		this.owner = owner;
	}

	public int getSuscriptors() {
		return suscriptors;
	}

	public void setSuscriptors(int suscriptors) {
		this.suscriptors = suscriptors;
	}

	public boolean isUserSub() {
		return userSub;
	}

	public void setUserSub(boolean userSub) {
		this.userSub = userSub;
	}

}
