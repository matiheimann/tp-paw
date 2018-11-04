package ar.edu.itba.pawddit.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "voteposts")
public class VotePost {
	
	@EmbeddedId
    private VotePostPK pk;
	
	@Column(name = "valuevote", nullable = false)
	private int value;
	
	/* package */ VotePost() {
		// Just for Hibernate, we love you!
	}
	
	public VotePost(final User user, final Post post, final int value) {
		this.pk = new VotePostPK(user, post);
		this.value = value;
	}
	
	public VotePostPK getPk() {
		return pk;
	}

	public void setPk(VotePostPK pk) {
		this.pk = pk;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

}
