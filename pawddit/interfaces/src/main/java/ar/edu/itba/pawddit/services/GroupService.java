package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface GroupService {
	
	public Optional<Group> findByName(String name);
	public Group create(String name, Timestamp date, String description, User user);
	public List<Group> findAll();
	public List<Group> getSuscribed(User user);
	public int deleteByName(User user, Group group);
	
}
