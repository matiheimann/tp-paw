package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.CommentVoteDao;

@Service
@Transactional
public class CommentVoteServiceImpl implements CommentVoteService {
	
	@Autowired
	CommentVoteDao commentVoteDao;

	@Override
	public void upVote(User user, Comment comment) {
		final int vote = comment.getUserVote();
		if (vote == 1) {
			commentVoteDao.cancelVote(user, comment);
		}
		else if (vote == -1) {
			commentVoteDao.changeVote(user, comment);
		}
		else {
			commentVoteDao.voteComment(user, comment, 1);
		}
		
	}

	@Override
	public void downVote(User user, Comment comment) {
		final int vote = comment.getUserVote();
		if (vote == -1) {
			commentVoteDao.cancelVote(user, comment);
		}
		else if (vote == 1) {
			commentVoteDao.changeVote(user, comment);
		}
		else {
			commentVoteDao.voteComment(user, comment, -1);
		}
		
	}

	@Override
	public int checkVote(User user, Comment comment) {
		return commentVoteDao.checkVote(user, comment);
	}

}
