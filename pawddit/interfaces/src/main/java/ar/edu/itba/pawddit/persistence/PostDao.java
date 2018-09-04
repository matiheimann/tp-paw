package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostDao {
	
	public Optional<Post> findById(long id);
	public Post create(String content, Timestamp date, Group group, User user);
	
}
