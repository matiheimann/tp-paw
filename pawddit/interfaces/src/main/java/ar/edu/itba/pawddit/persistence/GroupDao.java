package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface GroupDao {
	
	public Optional<Group> findByName(String name);
	public Group create(String name, Timestamp date, String description, User owner);
	public List<Group> searchGroupsByString(String name, int limit, int offset);
	public int searchGroupsByStringCount(String name);
	public List<Group> findSubscribedByUser(User user, int limit, int offset);
	public int findSubscribedByUserCount(User user);
	public List<Group> findRecommendedByUser(User user, int limit);
	public void delete(Group group);
	public List<String> search5NamesByString(String name);

}
