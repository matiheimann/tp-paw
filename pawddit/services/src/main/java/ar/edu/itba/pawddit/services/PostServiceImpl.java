package ar.edu.itba.pawddit.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.CommentDao;
import ar.edu.itba.pawddit.persistence.PostDao;
import ar.edu.itba.pawddit.persistence.PostVoteDao;
import ar.edu.itba.pawddit.persistence.SubscriptionDao;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private SubscriptionDao subscriptionDao;
	
	@Autowired
	private PostVoteDao postVoteDao;

	@Override
	public Post create(final String title, final String content, final LocalDateTime date, final Group group, final User user, final String imageId) {
		if (!subscriptionDao.isUserSub(user, group))
			throw new NoPermissionsException();
		return postDao.create(title, content, date, group, user, imageId);
	}
	
	@Override
	public List<Post> findAll(final int limit, final int offset, final String sort, final String time) {
		final List<Post> posts = postDao.findAll(limit, offset, sort, time);
		for (final Post post : posts) {
			post.setComments(commentDao.findByPostCount(post));
		}
		return posts;
	}
	
	@Override
	public List<Post> findByGroup(final Group group, final int limit, final int offset, final String sort, final String time) {
		final List<Post> posts = postDao.findByGroup(group, limit, offset, sort, time);
		for (final Post post : posts) {
			post.setComments(commentDao.findByPostCount(post));
		}
		return posts;
	}

	@Override
	public List<Post> findByUser(final User user, final int limit, final int offset, final String sort, final String time) {
		final List<Post> posts = postDao.findByUser(user, limit, offset, sort, time);
		for (final Post post : posts) {
			post.setComments(commentDao.findByPostCount(post));
		}
		return posts;
	}
	
	@Override
	public Optional<Post> findById(final User user, final Group group, final long id) {
		final Optional<Post> post = postDao.findById(group, id);
		if (post.isPresent()) {
			final Post p = post.get();
			p.setComments(commentDao.findByPostCount(p));
			if (user != null)
				p.setUserVote(postVoteDao.checkVote(user, p));
		}
		return post;
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user, final int limit, final int offset, final String sort, final String time) {
		final List<Post> posts = postDao.findBySubscriptions(user, limit, offset, sort, time);
		for (final Post post : posts) {
			post.setComments(commentDao.findByPostCount(post));
		}
		return posts;
	}

	@Override
	public int findAllCount(final String time) {
		return postDao.findAllCount(time);
	}

	@Override
	public int findByGroupCount(final Group group, final String time) {
		return postDao.findByGroupCount(group, time);
	}

	@Override
	public int findByUserCount(final User user, final String time) {
		return postDao.findByUserCount(user, time);
	}

	@Override
	public int findBySubscriptionsCount(final User user, final String time) {
		return postDao.findBySubscriptionsCount(user, time);
	}

	@Override
	public void delete(final User user, final Group group, final Post post) {
		if (!user.getIsAdmin() && user.getUserid() != group.getOwner().getUserid() && user.getUserid() != post.getOwner().getUserid())
			throw new NoPermissionsException();
		postDao.delete(post);
	}

}
