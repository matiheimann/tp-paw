package ar.edu.itba.pawddit.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

@Repository
public class SubscriptionHibernateDao implements SubscriptionDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void suscribe(final User user, final Group group) {
		final User u = em.merge(user);
		final Group g = em.merge(group);
		u.addSubscribedGroup(g);
	}

	@Override
	public void unsuscribe(final User user, final Group group) {
		final User u = em.merge(user);
		final Group g = em.merge(group);
		u.removeSubscribedGroup(g);
	}

	@Override
	public boolean isUserSub(final User user, final Group group) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Group as g join g.subscribedUsers as su where g = :group and su = :user", Long.class);
		query.setParameter("user", user);
		query.setParameter("group", group);
		return (query.getSingleResult().intValue() == 1) ? true : false;
	}

}
