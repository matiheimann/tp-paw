package ar.edu.itba.pawddit.services;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.User;

public interface CommentVoteService {
	
	public void upVote(User user, Comment comment);
	public void downVote(User user, Comment comment);
	public int checkVote(User user, Comment comment);

}
