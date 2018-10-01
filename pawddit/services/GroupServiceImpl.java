package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.GroupDao;
import ar.edu.itba.pawddit.services.exceptions.GroupAlreadyExists;
import ar.edu.itba.pawddit.services.exceptions.NotOwnerOfGroupException;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired 
	private SubscriptionService subscriptionService;

	@Override
	public Optional<Group> findByName(final String name) {
		return groupDao.findByName(name);
	}

	@Override
	public Group create(final String name, final Timestamp date, final String description, final User user) {
		if (findByName(name).isPresent())
			throw new GroupAlreadyExists();
		
		Group group = groupDao.create(name, date, description, user);
		subscriptionService.suscribe(user, group);
		
		return group;
	}
	
	@Override
	public List<Group> findAll() {
		return groupDao.findAll();
	}

	@Override
	public List<Group> getSuscribed(User user) {
		return groupDao.getSuscribed(user);
	}

	@Override
	public int deleteByName(User user, Group group) {
		if (user != group.getOwner())
			throw new NotOwnerOfGroupException();
		return groupDao.deleteByName(group.getName());
	}

	@Override
	public List<Group> searchByName(String name) {
		return groupDao.searchByName(name);
	}

	@Override
	public List<Group> searchByInterest(User user) {
		return groupDao.searchByInterest(user);
	}
	
}
