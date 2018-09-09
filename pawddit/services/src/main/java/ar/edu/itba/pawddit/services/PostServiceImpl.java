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
	public Post create(final String title, final String content, final Timestamp date, final Group group, final User user) {
		return postDao.create(title, content, date, group, user);
	}
	
	@Override
	public List<Post> findAll() {
		return postDao.findAll();
	}
	
	@Override
	public List<Post> findByGroup(final Group group) {
		return postDao.findByGroup(group);
	}

	@Override
	public List<Post> findByUser(final User user) {
		return postDao.findByUser(user);
	}
	
	@Override
	public Optional<Post> findById(final long id) {
		return postDao.findById(id);
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user){
		return postDao.findBySubscriptions(user);
	}

}
