package ar.edu.itba.pawddit.model;

import com.sun.jmx.snmp.Timestamp;

public class Post {
	private String content;
	private Timestamp date;
	private String group;
	private User owner;
	private long postid;
	
	public Post(String content, Timestamp date, String group, User owner, long postid) {
		this.content = content;
		this.date = date;
		this.group = group;
		this.owner = owner;
		this.postid = postid;
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
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
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
}
