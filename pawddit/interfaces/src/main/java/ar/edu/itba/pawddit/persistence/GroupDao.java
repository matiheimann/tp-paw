package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface GroupDao {
	
	public Optional<Group> findByName(String name);
	public Group create(String name, Timestamp date, String description, User owner);

}
