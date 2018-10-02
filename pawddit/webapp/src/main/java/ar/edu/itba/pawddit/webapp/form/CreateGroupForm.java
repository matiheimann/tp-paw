package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateGroupForm {
	
	@Pattern(regexp = "[a-zA-Z0-9]+")
	@Size(min = 4, max = 32)
	private String name;
	
	@Size(min = 6, max = 1000)
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
