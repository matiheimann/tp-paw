package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.GroupDao;

public class GroupServiceImpl implements GroupService {
	
	@Autowired
	GroupDao groupDao;

	@Override
	public Optional<Group> findByName(final String name) {
		return groupDao.findByName(name);
	}

	@Override
	public Group create(final String name, final Timestamp date, final String description, final User user) {
		return groupDao.create(name, date, description, user);
	}
	
}
