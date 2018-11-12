package ar.edu.itba.pawddit.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

public interface PostService {

	public Post create(String title, String content, LocalDateTime date, Group group, User user, String image);
	public List<Post> findAll(int limit, int offset, String sort, String time);
	public int findAllCount(String time);
	public List<Post> findByGroup(Group group, int limit, int offset, String sort, String time);
	public int findByGroupCount(Group group, String time);
	public List<Post> findByUser(User user, int limit, int offset, String sort, String time);
	public int findByUserCount(User user, String time);
	public Optional<Post> findById(User user, Group group, long id);
	public List<Post> findBySubscriptions(User user, int limit, int offset, String sort, String time);
	public int findBySubscriptionsCount(User user, String time);
	public void delete(User user, Group group, Post post);
	
}
