package ar.edu.itba.pawddit.webapp.dto;

import java.util.List;

public class GroupsDto {
	
	private List<GroupDto> groups;
	private int pageCount;
	
	public static GroupsDto fromGroups(List<GroupDto> groups, int pageCount) {
		final GroupsDto dto = new GroupsDto();
		dto.groups = groups;
		dto.pageCount = pageCount;
		return dto;
	}

	public List<GroupDto> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDto> groups) {
		this.groups = groups;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
