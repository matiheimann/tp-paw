package ar.edu.itba.pawddit.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface CommentService {
	
	public Comment create(String content, Post post, Comment replyTo, User user, LocalDateTime creationDate);
	public List<Comment> findByUser(User user, int limit, int offset);
	public int findByUserCount(User user);
	public List<Comment> findByPostNoReply(User user, Post post, int limit, int offset);
	public int findByPostNoReplyCount(Post post);
	public int findByPostCount(Post post);
	public Optional<Comment> findById(User user, Post post, long id);
	public List<Comment> findRepliesByComment(User user, Comment comment, int limit, int offset);
	public int findRepliesByCommentCount(Comment comment);
	public void delete(User user, Group group, Post post, Comment comment);
	
}
