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
	PostVoteDao postVoteDao;

	@Override
	public void upVote(final User user, final Post post) {
		final int vote = post.getUserVote();
		if (vote == 1) {
			postVoteDao.cancelVote(user, post);
		}
		else if (vote == -1) {
			postVoteDao.changeVote(user, post);
		}
		else {
			postVoteDao.votePost(user, post, 1);
		}
	}
	
	@Override
	public void downVote(final User user, final Post post) {
		final int vote = post.getUserVote();
		if (vote == -1) {
			postVoteDao.cancelVote(user, post);
		}
		else if (vote == 1) {
			postVoteDao.changeVote(user, post);
		}
		else {
			postVoteDao.votePost(user, post, -1);
		}
	}

	@Override
	public int checkVote(final User user, final Post post) {
		return postVoteDao.checkVote(user, post);
	}
}
