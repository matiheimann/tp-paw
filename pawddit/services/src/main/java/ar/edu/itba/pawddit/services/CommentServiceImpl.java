package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.persistence.CommentDao;

public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentDao commentDao;
	
	@Override
	public Optional<Comment> getCommentById(long id) {
		return commentDao.findById(id);
	}

	@Override
	public Comment create(int replyTo, Timestamp creationDate, String comment, long idPost, long user) {
		return commentDao.create(replyTo, creationDate, comment, idPost, user);
	}

}
