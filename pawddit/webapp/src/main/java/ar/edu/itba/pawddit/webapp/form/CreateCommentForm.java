package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Size;

public class CreateCommentForm {

	@Size(min = 1, max = 1000)
	private String content;
	
	private Integer replyTo;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Integer replyTo) {
		this.replyTo = replyTo;
	}
}
