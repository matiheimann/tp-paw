package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface GroupService {
	
	public Optional<Group> findByName(final String name);
	public Group create(final String name, final Timestamp date, final String description, final User user);
	
}
