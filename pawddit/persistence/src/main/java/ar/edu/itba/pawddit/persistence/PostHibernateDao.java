package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@Repository
public class PostHibernateDao implements PostDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Post create(final String title, final String content, final Timestamp date, final Group group, final User user, final String imageId) {
		final Post post = new Post(title, content, date, group, user, imageId);
		em.persist(post);
		return post;
	}
	
	@Override
	public List<Post> findAll(final int limit, final int offset, final String sort) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p left join p.votesSet as v group by p " + getOrderBySort(sort), Post.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List<Post> findByGroup(final Group group, final int limit, final int offset, final String sort) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p left join p.votesSet as v where p.group = :group group by p " + getOrderBySort(sort), Post.class);
		query.setParameter("group", group);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List<Post> findByUser(final User user, final int limit, final int offset, final String sort) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p left join p.votesSet as v where p.owner = :user group by p " + getOrderBySort(sort), Post.class);
		query.setParameter("user", user);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Optional<Post> findById(final Group group, final long id) {
		final TypedQuery<Post> query = em.createQuery("from Post as p where p.group = :group and p.postid = :id", Post.class);
		query.setParameter("group", group);
		query.setParameter("id", id);
		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user, final int limit, final int offset, final String sort) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p join p.group.subscribedUsers as s left join p.votesSet as v where s = :user group by p " + getOrderBySort(sort), Post.class);
		query.setParameter("user", user);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public int findAllCount() {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p", Long.class);
		return query.getSingleResult().intValue();
	}

	@Override
	public int findByGroupCount(final Group group) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p where p.group = :group", Long.class);
		query.setParameter("group", group);
		return query.getSingleResult().intValue();
	}

	@Override
	public int findByUserCount(final User user) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p where p.owner = :user", Long.class);
		query.setParameter("user", user);
		return query.getSingleResult().intValue();
	}

	@Override
	public int findBySubscriptionsCount(final User user) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p join p.group.subscribedUsers as su where su = :user", Long.class);
		query.setParameter("user", user);
		return query.getSingleResult().intValue();
	}

	@Override
	public void delete(final Post post) {
		final Post p = em.merge(post);
		em.remove(p);
	}
	
	private String getOrderBySort(final String sort) {
		if (sort == null || sort.equals("new"))
			return "order by p.date desc";
		
		if (sort.equals("top"))
			return " order by coalesce(sum(v.value), 0) desc";
		
		return "order by p.date desc";
	}


}
