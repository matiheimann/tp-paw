package ar.edu.itba.pawddit.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "votecomments")
public class VoteComment {
	
	@EmbeddedId
    private VoteCommentPK pk;
	
	@Column(name = "valuevote", nullable = false)
	private int value;
	
	/* package */ VoteComment() {
		// Just for Hibernate, we love you!
	}
	
	public VoteComment(final User user, final Comment comment, final int value) {
		this.pk = new VoteCommentPK(user, comment);
		this.value = value;
	}
	
	public VoteCommentPK getPk() {
		return pk;
	}

	public void setPk(VoteCommentPK pk) {
		this.pk = pk;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
}
