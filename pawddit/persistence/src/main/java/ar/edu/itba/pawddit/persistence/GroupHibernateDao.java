package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
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
	public Group create(final String name, final Timestamp date, final String description, final User owner) {
		final Group group = new Group(name, date, description, owner);
		em.persist(group);
		return group;
	}
	
	@Override
	public List<Group> findAll() {
		final TypedQuery<Group> query = em.createQuery("from Group", Group.class);
		return query.getResultList();
	}

	@Override
	public List<Group> getSuscribed(final User user) {
		final TypedQuery<Group> query = em.createQuery("select g from Group as g join g.subscribedUsers as s where s = :user", Group.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	@Override
	public void deleteByName(final String name) {
		final Group g = findByName(name).get();
		em.remove(g);
	}

	@Override
	public List<String> searchByName(final String name) {
		final TypedQuery<String> query = em.createQuery("select name from Group as g where upper(g.name) like upper(:name)", String.class);
		query.setParameter("name", "%" + name + "%");
		query.setMaxResults(5);
		return query.getResultList();
	}
	
}
