package ar.edu.itba.pawddit.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class VoteCommentPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "userid")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "commentid")
	private Comment comment;
	
	/* package */ VoteCommentPK() {
		// Just for Hibernate, we love you!
	}
	
	public VoteCommentPK(final User user, final Comment comment) {
		this.user = user;
		this.comment = comment;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        VoteCommentPK that = (VoteCommentPK) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(comment, that.comment);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(user, comment);
    }
    
}
