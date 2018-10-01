package ar.edu.itba.pawddit.model;

import java.sql.Timestamp;

public class Comment {
	
	private String content;
	private Post post;
	private Comment replyTo;
	private User owner;
	private Timestamp date;
	private long commentid;
	private Integer votes;
	
	public Comment(String content, Post post, Comment replyTo, User owner, Timestamp date, Integer votes, long commentid) {
		this.content = content;
		this.post = post;
		this.replyTo = replyTo;
		this.owner = owner;
		this.date = date;
		this.commentid = commentid;
		this.votes = votes;
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
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public long getCommentid() {
		return commentid;
	}
	
	public void setCommentid(long commentid) {
		this.commentid = commentid;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}
}
