package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface GroupService {
	
	public Optional<Group> findByName(String name);
	public Group create(String name, Timestamp date, String description, User user);
	public List<Group> searchGroupsByString(String name, int limit, int offset);
	public int searchGroupsByStringCount(String name);
	public List<Group> findSubscribedByUser(User user, int limit, int offset);
	public int findSubscribedByUserCount(User user);
	public List<Group> findRecommendedByUser(User user);
	public void delete(User user, Group group);
	public List<String> search5NamesByString(String name);
	
}
