package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.PostDao;
import ar.edu.itba.pawddit.persistence.SubscriptionDao;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;
	
	@Autowired
	private SubscriptionDao subscriptionDao;

	@Override
	public Post create(final String title, final String content, final Timestamp date, final Group group, final User user, final String imageId) {
		if (!subscriptionDao.isUserSub(user, group))
			throw new NoPermissionsException();
		return postDao.create(title, content, date, group, user, imageId);
	}
	
	@Override
	public List<Post> findAll(final int limit, final int offset, final String sort) {
		final List<Post> posts = postDao.findAll(limit, offset, sort);
		for (final Post post : posts) {
			post.getComments();
		}
		return posts;
	}
	
	@Override
	public List<Post> findByGroup(final Group group, final int limit, final int offset, final String sort) {
		final List<Post> posts = postDao.findByGroup(group, limit, offset, sort);
		for (final Post post : posts) {
			post.getComments();
		}
		return posts;
	}

	@Override
	public List<Post> findByUser(final User user, final int limit, final int offset, final String sort) {
		final List<Post> posts = postDao.findByUser(user, limit, offset, sort);
		for (final Post post : posts) {
			post.getComments();
		}
		return posts;
	}
	
	@Override
	public Optional<Post> findById(final Group group, final long id) {
		final Optional<Post> post = postDao.findById(group, id);
		if (post.isPresent()) {
			post.get().getComments();
		}
		return post;
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user, final int limit, final int offset, final String sort) {
		final List<Post> posts = postDao.findBySubscriptions(user, limit, offset, sort);
		for (final Post post : posts) {
			post.getComments();
		}
		return posts;
	}

	@Override
	public int findAllCount() {
		return postDao.findAllCount();
	}

	@Override
	public int findByGroupCount(final Group group) {
		return postDao.findByGroupCount(group);
	}

	@Override
	public int findByUserCount(final User user) {
		return postDao.findByUserCount(user);
	}

	@Override
	public int findBySubscriptionsCount(final User user) {
		return postDao.findBySubscriptionsCount(user);
	}

	@Override
	public void deletePost(final User user, final Group group, final Post post) {
		if (!user.getIsAdmin() && user.getUserid() != group.getOwner().getUserid() && user.getUserid() != post.getOwner().getUserid())
			throw new NoPermissionsException();
		postDao.deleteById(group, post.getPostid());
	}

}
