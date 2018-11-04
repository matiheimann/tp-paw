package ar.edu.itba.pawddit.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class VotePostPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "userid")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "postid")
	private Post post;
	
	/* package */ VotePostPK() {
		// Just for Hibernate, we love you!
	}
	
	public VotePostPK(final User user, final Post post) {
		this.user = user;
		this.post = post;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        VotePostPK that = (VotePostPK) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(post, that.post);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }
    
}
