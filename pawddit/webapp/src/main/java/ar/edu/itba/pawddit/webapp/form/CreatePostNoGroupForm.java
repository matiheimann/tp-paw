package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Size;

public class CreatePostNoGroupForm extends CreatePostForm {
	
	@Size(min = 4, max = 32)
	private String groupName;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
