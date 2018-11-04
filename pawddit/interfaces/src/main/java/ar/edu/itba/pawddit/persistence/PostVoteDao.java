package ar.edu.itba.pawddit.persistence;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VotePost;

public interface PostVoteDao {
	
	public VotePost votePost(User user, Post post, Integer value);
	public void changeVote(User user, Post post);
	public void cancelVote(User user, Post post);
	public int checkVote(User user, Post post);

}
