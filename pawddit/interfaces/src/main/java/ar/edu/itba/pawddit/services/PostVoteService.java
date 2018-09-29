package ar.edu.itba.pawddit.services;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostVoteService {
	
	public Boolean upVote(User user, Post post);
	public Boolean downVote(User user, Post post);
	public Integer checkVote(User user, Post post);

}
