package ar.edu.itba.pawddit.webapp.dto;

import java.time.LocalDateTime;

import ar.edu.itba.pawddit.model.Comment;

public class CommentDto {

	private String content;
	private PostDto post;
	private CommentDto replyTo;
	private UserDto owner;
	private LocalDateTime date;
	private long commentid;
	private int votes;
	private int userVote;
	private int replies;
	
	public static CommentDto fromComment(Comment comment) {
		if (comment == null)
			return null;
		
		final CommentDto dto = new CommentDto();
		dto.content = comment.getContent();
		dto.post = PostDto.fromPost(comment.getPost());
		dto.replyTo = fromCommentWithoutPostAndReplyTo(comment.getReplyTo());
		dto.owner = UserDto.fromUser(comment.getOwner());
		dto.date = comment.getDate();
		dto.commentid = comment.getCommentid();
		dto.votes = comment.getVotes();
		dto.userVote = comment.getUserVote();
		dto.replies = comment.getReplies();
		return dto;
	}
	
	public static CommentDto fromCommentWithoutPost(Comment comment) {
		if (comment == null)
			return null;
		
		final CommentDto dto = new CommentDto();
		dto.content = comment.getContent();
		dto.replyTo = fromCommentWithoutPostAndReplyTo(comment.getReplyTo());
		dto.owner = UserDto.fromUser(comment.getOwner());
		dto.date = comment.getDate();
		dto.commentid = comment.getCommentid();
		dto.votes = comment.getVotes();
		dto.userVote = comment.getUserVote();
		dto.replies = comment.getReplies();
		return dto;
	}
	
	public static CommentDto fromCommentWithoutPostAndReplyTo(Comment comment) {
		if (comment == null)
			return null;
		
		final CommentDto dto = new CommentDto();
		dto.content = comment.getContent();
		dto.owner = UserDto.fromUser(comment.getOwner());
		dto.date = comment.getDate();
		dto.commentid = comment.getCommentid();
		dto.votes = comment.getVotes();
		dto.userVote = comment.getUserVote();
		dto.replies = comment.getReplies();
		return dto;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PostDto getPost() {
		return post;
	}

	public void setPost(PostDto post) {
		this.post = post;
	}

	public CommentDto getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(CommentDto replyTo) {
		this.replyTo = replyTo;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
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

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}
	
}
