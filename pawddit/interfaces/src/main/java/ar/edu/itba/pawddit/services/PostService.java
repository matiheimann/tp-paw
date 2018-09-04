package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostService {

	public Post create(final String content, final Timestamp date, final Group group, final User user);
	public List<Post> findByGroup(final Group group);
	
}
