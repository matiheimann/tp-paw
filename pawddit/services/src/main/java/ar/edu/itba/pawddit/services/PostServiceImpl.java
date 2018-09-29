package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.PostDao;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;

	@Override
	public Post create(final String title, final String content, final Timestamp date, final Group group, final User user, final String imageId) {
		return postDao.create(title, content, date, group, user, imageId);
	}
	
	@Override
	public List<Post> findAll(final int limit, final int offset) {
		return postDao.findAll(limit, offset);
	}
	
	@Override
	public List<Post> findByGroup(final Group group, final int limit, final int offset) {
		return postDao.findByGroup(group, limit, offset);
	}

	@Override
	public List<Post> findByUser(final User user, final int limit, final int offset) {
		return postDao.findByUser(user, limit, offset);
	}
	
	@Override
	public Optional<Post> findById(final Group group, final long id) {
		return postDao.findById(group, id);
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user, final int limit, final int offset){
		return postDao.findBySubscriptions(user, limit, offset);
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

}
