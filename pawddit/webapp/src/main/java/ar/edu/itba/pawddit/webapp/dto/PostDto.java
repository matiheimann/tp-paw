package ar.edu.itba.pawddit.webapp.dto;

import java.time.LocalDateTime;

import ar.edu.itba.pawddit.model.Post;

public class PostDto {
	
	private String title;
	private String content;
	private LocalDateTime date;
	private GroupDto group;
	private UserDto owner;
	private long postid;
	private String imageid;
	private int comments;
	private int votes;
	private int userVote;
	
	public static PostDto fromPost(Post post) {
		final PostDto dto = new PostDto();
		if (post != null) {
			dto.title = post.getTitle();
			dto.content = post.getContent();
			dto.date = post.getDate();
			dto.group = GroupDto.fromGroup(post.getGroup());
			dto.owner = UserDto.fromUser(post.getOwner());
			dto.postid = post.getPostid();
			dto.imageid = post.getImageid();
			dto.comments = post.getComments();
			dto.votes = post.getVotes();
			dto.userVote = post.getUserVote();
		}
		return dto;
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

	public GroupDto getGroup() {
		return group;
	}

	public void setGroup(GroupDto group) {
		this.group = group;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
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
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getUserVote() {
		return userVote;
	}

	public void setUserVote(int userVote) {
		this.userVote = userVote;
	}
	
}
