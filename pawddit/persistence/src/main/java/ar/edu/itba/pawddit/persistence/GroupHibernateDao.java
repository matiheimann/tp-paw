package ar.edu.itba.pawddit.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

@Repository
public class GroupHibernateDao implements GroupDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<Group> findByName(final String name){
		final TypedQuery<Group> query = em.createQuery("from Group as g where g.name = :name", Group.class);
		query.setParameter("name", name);
		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public Group create(final String name, final LocalDateTime date, final String description, final User owner) {
		final Group group = new Group(name, date, description, owner);
		em.persist(group);
		return group;
	}
	
	@Override
	public List<Group> searchGroupsByString(final String name, final int limit, final int offset) {
		final TypedQuery<Group> query = em.createQuery("from Group as g where upper(g.name) like upper(:name)", Group.class);
		query.setParameter("name", "%" + name + "%");
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public int searchGroupsByStringCount(final String name) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Group as g where upper(g.name) like upper(:name)", Long.class);
		query.setParameter("name", "%" + name + "%");
		return query.getSingleResult().intValue();
	}

	@Override
	public List<Group> findSubscribedByUser(final User user, final int limit, final int offset) {
		final TypedQuery<Group> query = em.createQuery("select g from Group as g join g.subscribedUsers as su where su = :user", Group.class);
		query.setParameter("user", user);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public int findSubscribedByUserCount(final User user) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Group as g join g.subscribedUsers as su where su = :user", Long.class);
		query.setParameter("user", user);
		return query.getSingleResult().intValue();
	}
	
	@Override
	public List<Group> findRecommendedByUser(final User user, final int limit) {
		final TypedQuery<Group> query = em.createQuery("select g1 from Group as g1 join g1.subscribedUsers as su1 " +
				"where g1 not in (select g2 from Group as g2 join g2.subscribedUsers as su2 where su2 = :user) " +
					"and su1 in (select u from User as u join u.subscribedGroups as sg where u <> :user " +
						"and sg in (select g3 from Group as g3 join g3.subscribedUsers as su3 where su3 = :user)) " +
				"group by g1 " + 
				"order by count(distinct su1) desc", Group.class);
		query.setParameter("user", user);
		query.setMaxResults(limit);
		List<Group> groups = query.getResultList();
		if (groups.size() < limit) {
			if (!groups.isEmpty()) {
				final TypedQuery<Group> query2 = em.createQuery("select g1 from Group as g1 join g1.subscribedUsers as su1 " +
						"where g1 not in (:groups) " + 
							"and g1 not in (select g2 from Group as g2 join g2.subscribedUsers as su2 where su2 = :user) " +
							"group by g1 order by count(distinct su1) desc", Group.class);
				query2.setParameter("groups", groups);
				query2.setParameter("user", user);
				query2.setMaxResults(limit - groups.size());
				groups.addAll(query2.getResultList());
			}
			else {
				final TypedQuery<Group> query2 = em.createQuery("select g1 from Group as g1 join g1.subscribedUsers as su1 " +
						"where g1 not in (select g2 from Group as g2 join g2.subscribedUsers as su2 where su2 = :user) " +
						"group by g1 order by count(distinct su1) desc", Group.class);
				query2.setParameter("user", user);
				query2.setMaxResults(limit);
				groups = query2.getResultList();
			}
		}
		return groups;
	}

	@Override
	public void delete(final Group group) {
		final Group g = em.merge(group);
		em.remove(g);
	}

	@Override
	public List<String> search5NamesByString(final String name) {
		final TypedQuery<String> query = em.createQuery("select name from Group as g where upper(g.name) like upper(:name)", String.class);
		query.setParameter("name", "%" + name + "%");
		query.setMaxResults(5);
		return query.getResultList();
	}
	
}
