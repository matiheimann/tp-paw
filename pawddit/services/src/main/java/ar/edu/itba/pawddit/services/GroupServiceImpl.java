package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.persistence.GroupDao;

public class GroupServiceImpl implements GroupService {
	
	@Autowired
	GroupDao groupDao;

	@Override
	public Optional<Group> findByName(String name) {
		return groupDao.findByName(name);
	}

	@Override
	public Group create(String name, Timestamp date, String description, long idUser) {
		return groupDao.create(name, date, description, idUser);
	}
	
}
