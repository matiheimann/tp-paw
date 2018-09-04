package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.CommentDao;

public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentDao commentDao;
	
	@Override
	public Optional<Comment> getCommentById(final long id) {
		return commentDao.findById(id);
	}

	@Override
	public Comment create(final Comment replyTo, final Timestamp creationDate, final String comment, final Post post, final User user) {
		return commentDao.create(replyTo, creationDate, comment, post, user);
	}

}
