package ar.edu.itba.pawddit.services;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostVoteService {
	
	public void upVote(User user, Post post);
	public void downVote(User user, Post post);
	public int checkVote(User user, Post post);

}
