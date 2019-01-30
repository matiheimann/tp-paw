package ar.edu.itba.pawddit.webapp.form;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.itba.pawddit.webapp.form.annotations.ValidImageFormat;

@ValidImageFormat
public class PictureForm implements FormWithImage {

	private MultipartFile file;
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
