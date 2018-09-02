package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;

import ar.edu.itba.pawddit.model.Group;

public interface GroupService {
	
	public Group findByName(final String name);
	public Group create(final String name, final Timestamp date, final String description, final long idUser);
	
	
}
