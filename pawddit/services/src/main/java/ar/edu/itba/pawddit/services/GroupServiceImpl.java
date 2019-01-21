package ar.edu.itba.pawddit.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.GroupDao;
import ar.edu.itba.pawddit.persistence.SubscriptionDao;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired 
	private SubscriptionDao subscriptionDao;

	@Override
	public Optional<Group> findByName(final User user, final String name) {
		final Optional<Group> group = groupDao.findByName(name);
		if (group.isPresent()) {
			final Group g = group.get();
			g.setSuscriptors(groupDao.findSubscriptorsCount(g));
			if (user != null)
				g.setUserSub(subscriptionDao.isUserSub(user, g));
		}
		return group;
	}

	@Override
	public Group create(final String name, final LocalDateTime date, final String description, final User user) {	
		final Group group = groupDao.create(name, date, description, user);
		subscriptionDao.suscribe(user, group);
		return group;
	}
	
	@Override
	public List<Group> searchGroupsByString(String name, final int limit, final int offset) {
		final List<Group> groups = groupDao.searchGroupsByString(name, limit, offset);
		for (final Group group : groups) {
			group.setSuscriptors(groupDao.findSubscriptorsCount(group));
		}
		return groups;
	}
	
	@Override
	public int searchGroupsByStringCount(final String name) {
		return groupDao.searchGroupsByStringCount(name);
	}

	@Override
	public List<Group> findSubscribedByUser(final User user, final int limit, final int offset) {
		final List<Group> groups = groupDao.findSubscribedByUser(user, limit, offset);
		for (final Group group : groups) {
			group.setSuscriptors(groupDao.findSubscriptorsCount(group));
		}
		return groups;
	}
	
	@Override
	public int findSubscribedByUserCount(final User user) {
		return groupDao.findSubscribedByUserCount(user);
	}
	
	@Override
	public List<Group> findRecommendedByUser(final User user, final int limit) {
		final List<Group> groups = groupDao.findRecommendedByUser(user, limit);
		for (final Group group : groups) {
			group.setSuscriptors(groupDao.findSubscriptorsCount(group));
		}
		return groups;
	}

	@Override
	public void delete(final User user, final Group group) {
		if (!user.getIsAdmin() && user.getUserid() != group.getOwner().getUserid())
			throw new NoPermissionsException();
		groupDao.delete(group);
	}

	@Override
	public List<String> search5NamesByString(final String name) {
		return groupDao.search5NamesByString(name);
	}

	@Override
	public int findSubscriptorsCount(Group group) {
		return groupDao.findSubscriptorsCount(group);
	}
	
}
