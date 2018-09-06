package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Size;

public class CreateGroupForm {
	
	@Size(min = 4, max = 32)
	private String title;
	
	@Size(min = 6, max = 100)
	private String description;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
