package ar.edu.itba.pawddit.model;

import java.sql.Timestamp;

public class Post {
	private String title;
	private String content;
	private Timestamp date;
	private Group group;
	private User owner;
	private long postid;
	private int comments;
	private int votes;
	private String imageid;
	
	public Post(String title, String content, Timestamp date, Group group, User owner, long postid, int comments, int votes, String imageid) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.group = group;
		this.owner = owner;
		this.postid = postid;
		this.comments = comments;
		this.votes = votes;
		this.imageid = imageid;
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
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
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

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public String getImageid() {
		return imageid;
	}

	public void setImageid(String imageid) {
		this.imageid = imageid;
	}
}
