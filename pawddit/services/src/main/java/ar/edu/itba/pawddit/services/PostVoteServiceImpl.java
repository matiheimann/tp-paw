package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.PostVoteDao;

@Service
public class PostVoteServiceImpl implements PostVoteService {
	
	@Autowired
	PostVoteDao pvd;
	

	@Override
	public Boolean changeVote(User user, Post post) {
		return pvd.changeVote(user, post);
	}


	@Override
	public Boolean cancelVote(User user, Post post) {
		return pvd.cancelVote(user, post);
	}


	@Override
	public Boolean votePost(User user, Post post, Integer value) {
		return pvd.votePost(user, post, value);
	}


	@Override
	public Integer checkVote(User user, Post post) {
		return pvd.checkVote(user, post);
	}

}
