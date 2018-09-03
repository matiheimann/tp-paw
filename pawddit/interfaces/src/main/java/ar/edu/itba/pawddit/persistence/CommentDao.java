package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Comment;

public interface CommentDao {
	
	public Optional<Comment> findById(long id);
	public Comment create(int replyTo, Timestamp creationDate, String comment, long idPost, long user);

}
