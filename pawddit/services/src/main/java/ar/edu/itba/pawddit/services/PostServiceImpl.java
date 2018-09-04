package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.PostDao;

public class PostServiceImpl implements PostService {

	@Autowired
	PostDao postDao;
	
	@Override
	public Optional<Post> getPostById(long id) {
		return postDao.findById(id);
	}

	@Override
	public Post create(String content, Timestamp date, User user, String groupName) {
		return postDao.create(content, date, groupName, user);
	}

}
