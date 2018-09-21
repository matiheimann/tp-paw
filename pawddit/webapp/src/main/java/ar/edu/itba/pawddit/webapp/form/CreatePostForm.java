package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Size;

public class CreatePostForm {
	
	@Size(min = 4, max = 60)
	private String title;
	
	@Size(min = 6, max = 1000)
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
