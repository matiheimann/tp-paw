package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostService {

	public Optional<Post> getPostById(final long id);
	public Post create(final String content, final Timestamp date, final User user, Group group);
	
}
