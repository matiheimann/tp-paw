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
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired 
	private SubscriptionService subscriptionService;

	@Override
	public Optional<Group> findByName(final String name) {
		final Optional<Group> group = groupDao.findByName(name);
		if (group.isPresent()) {
			group.get().getSuscriptors();
		}
		return group;
	}

	@Override
	public Group create(final String name, final Timestamp date, final String description, final User user) {
		if (findByName(name).isPresent())
			throw new GroupAlreadyExists();
		
		final Group group = groupDao.create(name, date, description, user);
		subscriptionService.suscribe(user, group);
		
		return group;
	}
	
	@Override
	public List<Group> findAll() {
		return groupDao.findAll();
	}

	@Override
	public List<Group> getSuscribed(final User user) {
		final List<Group> groups = groupDao.getSuscribed(user);
		for (final Group group : groups) {
			group.getSuscriptors();
		}
		return groups;
	}

	@Override
	public void deleteGroup(final User user, final Group group) {
		if (!user.getIsAdmin() && user.getUserid() != group.getOwner().getUserid())
			throw new NoPermissionsException();
		groupDao.deleteByName(group.getName());
	}

	@Override
	public List<String> searchByName(final String name) {
		return groupDao.searchByName(name);
	}
	
}
