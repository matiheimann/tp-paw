package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.CommentDao;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public Comment create(final String content, final Post post, final Comment replyTo, final User user, final Timestamp creationDate) {
		return commentDao.create(content, post, replyTo, user, creationDate);
	}

	@Override
	public List<Comment> findByUser(final User user, final int limit, final int offset) {
		return commentDao.findByUser(user, limit, offset);
	}

	@Override
	public List<Comment> findByPost(final Post post, final int limit, final int offset) {
		return commentDao.findByPost(post, limit, offset);
	}

	@Override
	public Optional<Comment> findById(final long id) {
		return commentDao.findById(id);
	}

}
