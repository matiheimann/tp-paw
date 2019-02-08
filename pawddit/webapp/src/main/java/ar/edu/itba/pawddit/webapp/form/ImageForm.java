package ar.edu.itba.pawddit.webapp.form;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import ar.edu.itba.pawddit.webapp.form.annotations.ValidImageFormat;

@ValidImageFormat
public class ImageForm {

	@FormDataParam("image")
	private FormDataBodyPart fileBodyPart;
	
	public FormDataBodyPart getFileBodyPart() {
		return fileBodyPart;
	}

	public void setFileBodyPart(FormDataBodyPart fileBodyPart) {
		this.fileBodyPart = fileBodyPart;
	}
	
	public byte[] getFileBytes() {
		return getFileBodyPart().getValueAs(byte[].class);
	}

}
