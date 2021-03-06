package ar.edu.itba.pawddit.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface GroupService {
	
	public Optional<Group> findByName(User user, String name);
	public Group create(String name, LocalDateTime date, String description, User user);
	public List<Group> searchGroupsByString(String name, int limit, int offset);
	public int searchGroupsByStringCount(String name);
	public List<Group> findSubscribedByUser(User user, int limit, int offset);
	public int findSubscribedByUserCount(User user);
	public List<Group> findRecommendedByUser(User user, int limit);
	public void delete(User user, Group group);
	public List<String> search5NamesByString(String name);
	public int findSubscriptorsCount(Group group);
	
}
