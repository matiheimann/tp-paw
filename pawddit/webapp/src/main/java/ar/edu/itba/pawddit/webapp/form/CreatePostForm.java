package ar.edu.itba.pawddit.webapp.form;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.itba.pawddit.webapp.form.annotations.ValidImageFormat;

@ValidImageFormat
public class CreatePostForm implements FormWithImage {
	
	@Size(min = 4, max = 60)
	private String title;
	
	@Size(min = 6, max = 1000)
	private String content;
	
	private MultipartFile file;

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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
