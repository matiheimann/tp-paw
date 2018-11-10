package ar.edu.itba.pawddit.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "comments")
public class Comment {
	
	@Column(name = "content", length = 1000, nullable = false)
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "postid")
	private Post post;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "replyto")
	private Comment replyTo;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userid")
	private User owner;
	
	@Column(name = "creationdate", nullable = false)
	private LocalDateTime date;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_commentid_seq")
	@SequenceGenerator(sequenceName = "comments_commentid_seq", name = "comments_commentid_seq", allocationSize = 1)
	@Column(name = "commentid")
	private long commentid;
	
	@Transient
	private int votes;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "replyTo")
	private Set<Comment> replies = new HashSet<Comment>();
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "pk.comment")
	private Set<VoteComment> votesSet = new HashSet<VoteComment>();
	
	/* package */ Comment() {
		// Just for Hibernate, we love you!
	}
	
	public Comment(final String content, final Post post, final Comment replyTo, final User user, final LocalDateTime date) {
		this.content = content;
		this.post = post;
		this.replyTo = replyTo;
		this.owner = user;
		this.date = date;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Post getPost() {
		return post;
	}
	
	public void setPost(Post post) {
		this.post = post;
	}
	
	public Comment getReplyTo() {
		return replyTo;
	}
	
	public void setReplyTo(Comment replyTo) {
		this.replyTo = replyTo;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public long getCommentid() {
		return commentid;
	}
	
	public void setCommentid(long commentid) {
		this.commentid = commentid;
	}

	public int getVotes() {
		int votes = 0;
		for (final VoteComment vp : getVotesSet()) {
			votes += vp.getValue();
		}
		return votes;
	}
	
	public Set<Comment> getReplies() {
		return replies;
	}

	public void setReplies(Set<Comment> replies) {
		this.replies = replies;
	}


	public Set<VoteComment> getVotesSet() {
		return votesSet;
	}

	public void setVotesSet(Set<VoteComment> votesSet) {
		this.votesSet = votesSet;
	}
	
}
