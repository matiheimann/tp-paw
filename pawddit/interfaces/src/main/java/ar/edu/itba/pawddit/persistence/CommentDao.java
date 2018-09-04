package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface CommentDao {
	
	public Optional<Comment> findById(long id);
	public Comment create(final Comment replyTo, final Timestamp creationDate, final String comment, final Post post, final User user);

}
