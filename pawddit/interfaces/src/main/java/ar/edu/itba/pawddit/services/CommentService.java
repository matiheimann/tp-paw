package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Comment;

public interface CommentService {
	
	public Optional<Comment> getCommentById(long id);
	public Comment create(final int replyTo, final Timestamp creationDate, final String comment, final long idPost, final long user);
	
	
}
