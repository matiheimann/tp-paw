package ar.edu.itba.pawddit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "verificationtokens")
public class VerificationToken {
	
	@Id
	@Column(name = "token", length = 36)
	private String token;
	
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userid")
    private User user;
    
	/* package */ VerificationToken() {
		// Just for Hibernate, we love you!
	}

	public VerificationToken(final String token, final User user) {
		this.token = token;
		this.user = user;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
