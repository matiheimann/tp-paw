package ar.edu.itba.pawddit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image {
	
	@Id
	@Column(name = "token", length = 36)
	private String token;
	
	@Column(name = "bytearray")
	private byte[] byteArray;
	
	/* package */ Image() {
		// Just for Hibernate, we love you!
	}

	public Image(final byte[] image, final String token) {
		this.byteArray = image;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

}
