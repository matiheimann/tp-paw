package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.CommentVoteDao;

public class CommentVoteServiceImpl implements CommentVoteService {
	
	@Autowired
	CommentVoteDao cvd;

	@Override
	public void upVote(User user, Comment comment) {
		final Integer vote = cvd.checkVote(user, comment);
		if (vote == 1) {
			cvd.cancelVote(user, comment);
		}
		else if (vote == -1) {
			cvd.changeVote(user, comment);
		}
		else {
			cvd.voteComment(user, comment, 1);
		}
		
	}

	@Override
	public void downVote(User user, Comment comment) {
		final Integer vote = cvd.checkVote(user, comment);
		if (vote == -1) {
			cvd.cancelVote(user, comment);
		}
		else if (vote == 1) {
			cvd.changeVote(user, comment);
		}
		else {
			cvd.voteComment(user, comment, -1);
		}
		
	}

	@Override
	public int checkVote(User user, Comment comment) {
		return cvd.checkVote(user, comment);
	}

}
