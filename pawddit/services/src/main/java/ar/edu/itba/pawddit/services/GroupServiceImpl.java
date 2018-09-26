package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.GroupDao;
import ar.edu.itba.pawddit.services.exceptions.GroupAlreadyExists;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDao groupDao;

	@Override
	public Optional<Group> findByName(final String name) {
		return groupDao.findByName(name);
	}

	@Override
	public Group create(final String name, final Timestamp date, final String description, final User user) {
		if (findByName(name).isPresent())
			throw new GroupAlreadyExists();
		return groupDao.create(name, date, description, user);
	}
	
	@Override
	public List<Group> findAll() {
		return groupDao.findAll();
	}
	
}
