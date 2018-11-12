package ar.edu.itba.pawddit.webapp.form;

import org.springframework.web.multipart.MultipartFile;

public interface FormWithImage {

	public MultipartFile getFile();
	
	public void setFile(MultipartFile file);
}
