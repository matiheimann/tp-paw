package ar.edu.itba.pawddit.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.CommentDao;
import ar.edu.itba.pawddit.persistence.CommentVoteDao;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private CommentVoteDao commentVoteDao;

	@Override
	public Comment create(final String content, final Post post, final Comment replyTo, final User user, final LocalDateTime date) {
		return commentDao.create(content, post, replyTo, user, date);
	}

	@Override
	public List<Comment> findByUser(final User user, final int limit, final int offset) {
		final List<Comment> comments = commentDao.findByUser(user, limit, offset);
		for (final Comment comment : comments) {
			comment.getPost().getPostid();
			comment.setReplies(commentDao.findRepliesByCommentCount(comment));
			if(comment.getReplyTo() != null)
				comment.getReplyTo().getCommentid();
		}
		return comments;
	}
	
	@Override
	public List<Comment> findByPost(User user, Post post, int limit, int offset) {
		final List<Comment> comments = commentDao.findByPost(post, limit, offset);
		for (final Comment comment : comments) {
			if (user != null)
				comment.setUserVote(commentVoteDao.checkVote(user, comment));
			comment.setReplies(commentDao.findRepliesByCommentCount(comment));
			if(comment.getReplyTo() != null)
				comment.getReplyTo().getCommentid();
		}
		return comments;
	}

	@Override
	public List<Comment> findByPostNoReply(final User user, final Post post, final int limit, final int offset) {
		final List<Comment> comments = commentDao.findByPostNoReply(post, limit, offset);
		for (final Comment comment : comments) {
			if (user != null)
				comment.setUserVote(commentVoteDao.checkVote(user, comment));
			comment.setReplies(commentDao.findRepliesByCommentCount(comment));
		}
		return comments;
	}

	@Override
	public Optional<Comment> findById(final User user, final Post post, final long id) {
		final Optional<Comment> comment = commentDao.findById(post, id);
		if (comment.isPresent()) {
			final Comment c = comment.get();
			if (user != null)
				c.setUserVote(commentVoteDao.checkVote(user, c));
		}
		return comment;
	}

	@Override
	public int findByUserCount(final User user) {
		return commentDao.findByUserCount(user);
	}
	
	@Override
	public int findByPostNoReplyCount(final Post post) {
		return commentDao.findByPostNoReplyCount(post);
	}

	@Override
	public int findByPostCount(final Post post) {
		return commentDao.findByPostCount(post);
	}
	
	@Override
	public List<Comment> findRepliesByComment(final User user, final Comment comment, final int limit, final int offset) {
		final List<Comment> comments = commentDao.findRepliesByComment(comment, limit, offset);
		for (final Comment c : comments) {
			if (user != null)
				comment.setUserVote(commentVoteDao.checkVote(user, comment));
			c.setReplies(commentDao.findRepliesByCommentCount(c));
		}
		return comments;
	}

	@Override
	public int findRepliesByCommentCount(final Comment comment) {
		return commentDao.findRepliesByCommentCount(comment);
	}

	@Override
	public void delete(final User user, final Group group, final Post post, final Comment comment) {
		if (!user.getIsAdmin() && user.getUserid() != group.getOwner().getUserid() && user.getUserid() != post.getOwner().getUserid() && user.getUserid() != comment.getOwner().getUserid())
			throw new NoPermissionsException();
		commentDao.delete(comment);
	}

}
