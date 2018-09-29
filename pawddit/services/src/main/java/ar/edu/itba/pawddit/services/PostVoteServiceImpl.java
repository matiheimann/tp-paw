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
	public Boolean upVote(final User user, final Post post) {
		final Integer vote = checkVote(user, post);
		if (vote == 1)
			return pvd.cancelVote(user, post);
		else if (vote == -1)
			return pvd.changeVote(user, post);
		else
			return pvd.votePost(user, post, 1);
	}
	
	@Override
	public Boolean downVote(final User user, final Post post) {
		final Integer vote = checkVote(user, post);
		if (vote == -1)
			return pvd.cancelVote(user, post);
		else if (vote == 1)
			return pvd.changeVote(user, post);
		else
			return pvd.votePost(user, post, -1);
	}

	@Override
	public Integer checkVote(final User user, final Post post) {
		return pvd.checkVote(user, post);
	}

}
