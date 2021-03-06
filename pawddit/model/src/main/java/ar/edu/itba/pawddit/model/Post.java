package ar.edu.itba.pawddit.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "posts")
public class Post {
	
	@Column(name = "title", length = 60, nullable = false)
	private String title;
	
	@Column(name = "content", length = 1000, nullable = false)
	private String content;
	
	@Column(name = "creationdate", nullable = false)
	private LocalDateTime date;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "groupname")
	private Group group;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userid")
	private User owner;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_postid_seq")
	@SequenceGenerator(sequenceName = "posts_postid_seq", name = "posts_postid_seq", allocationSize = 1)
	@Column(name = "postid")
	private long postid;
	
	@Column(name = "imageid", length = 36, nullable = true)
	private String imageid;
	
	@Transient
	private int comments;
	
	@Transient
	private int votes;
	
	@Transient
	private int userVote;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "post")
	private List<Comment> commentsSet = new ArrayList<Comment>();
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "pk.post")
	private List<VotePost> votesSet = new ArrayList<VotePost>();
	
	/* package */ Post() {
		// Just for Hibernate, we love you!
	}
	
	public Post(final String title, final String content, final LocalDateTime date, final Group group, final User user, final String imageId) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.group = group;
		this.owner = user;
		this.imageid = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public long getPostid() {
		return postid;
	}
	
	public void setPostid(long postid) {
		this.postid = postid;
	}

	public String getImageid() {
		return imageid;
	}

	public void setImageid(String imageid) {
		this.imageid = imageid;
	}

	public int getComments() {
		return comments;
	}
	
	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getVotes() {
		int votes = 0;
		for (final VotePost vp : getVotesSet()) {
			votes += vp.getValue();
		}
		return votes;
	}

	public int getUserVote() {
		return userVote;
	}

	public void setUserVote(int userVote) {
		this.userVote = userVote;
	}

	public List<Comment> getCommentsSet() {
		return commentsSet;
	}

	public void setCommentsSet(List<Comment> commentsSet) {
		this.commentsSet = commentsSet;
	}

	public List<VotePost> getVotesSet() {
		return votesSet;
	}

	public void setVotesSet(List<VotePost> votesSet) {
		this.votesSet = votesSet;
	}
	
}
