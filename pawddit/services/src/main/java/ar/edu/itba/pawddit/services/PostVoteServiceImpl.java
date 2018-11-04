package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.PostVoteDao;

@Service
@Transactional
public class PostVoteServiceImpl implements PostVoteService {
	
	@Autowired
	PostVoteDao pvd;

	@Override
	public void upVote(final User user, final Post post) {
		final Integer vote = checkVote(user, post);
		if (vote == 1) {
			pvd.cancelVote(user, post);
		}
		else if (vote == -1) {
			pvd.changeVote(user, post);
		}
		else
			pvd.votePost(user, post, 1);
	}
	
	@Override
	public void downVote(final User user, final Post post) {
		final Integer vote = checkVote(user, post);
		if (vote == -1) {
			pvd.cancelVote(user, post);
		}
		else if (vote == 1) {
			pvd.changeVote(user, post);
		}
		else
			pvd.votePost(user, post, -1);
	}

	@Override
	public int checkVote(final User user, final Post post) {
		return pvd.checkVote(user, post);
	}
}
