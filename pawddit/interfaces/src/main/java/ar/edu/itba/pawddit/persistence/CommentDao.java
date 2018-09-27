package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface CommentDao {
	
	public Comment create(String content, Post post, Comment replyTo, User user, Timestamp creationDate);
	public List<Comment> findByUser(User user, int limit, int offset);
	public int findByUserCount(User user);
	public List<Comment> findByPost(Post post, int limit, int offset);
	public int findByPostCount(Post post);
	public Optional<Comment> findById(long id);

}
