package ar.edu.itba.pawddit.persistence;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostVoteDao {
	
	public Boolean votePost(User user, Post post, Integer value);
	public int changeVote(User user, Post post);
	public int cancelVote(User user, Post post);
	public Integer checkVote(User user, Post post);

}
