package ar.edu.itba.pawddit.persistence;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VoteComment;
import ar.edu.itba.pawddit.model.Comment;

public interface CommentVoteDao {
	
	public VoteComment voteComment(User user, Comment comment, Integer value);
	public void changeVote(User user, Comment comment);
	public void cancelVote(User user, Comment comment);
	public int checkVote(User user, Comment comment);

}
