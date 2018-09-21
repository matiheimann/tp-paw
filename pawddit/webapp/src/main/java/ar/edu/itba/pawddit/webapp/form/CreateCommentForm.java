package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Size;

public class CreateCommentForm {

	@Size(min = 1, max = 100)
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
