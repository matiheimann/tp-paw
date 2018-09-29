package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostDao {
	
	public Post create(String title, String content, Timestamp date, Group group, User user, String imageId);
	public List<Post> findAll(int limit, int offset);
	public int findAllCount();
	public List<Post> findByGroup(Group group, int limit, int offset);
	public int findByGroupCount(Group group);
	public List<Post> findByUser(User user, int limit, int offset);
	public int findByUserCount(User user);
	public Optional<Post> findById(Group group, long id);
	public List<Post> findBySubscriptions(User user, int limit, int offset);
	public int findBySubscriptionsCount(User user);
	
}
