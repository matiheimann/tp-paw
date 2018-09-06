package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostDao {
	
	public Post create(final String title, final String content, final Timestamp date, final Group group, final User user);
	public List<Post> findAll();
	public List<Post> findByGroup(final Group group);
	public List<Post> findByUser(final User user);
	public Optional<Post> findById(final long id);
	
}
