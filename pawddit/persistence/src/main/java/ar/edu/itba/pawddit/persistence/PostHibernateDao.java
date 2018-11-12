package ar.edu.itba.pawddit.persistence;

import java.time.LocalDateTime;
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
	public Post create(final String title, final String content, final LocalDateTime date, final Group group, final User user, final String imageId) {
		final Post post = new Post(title, content, date, group, user, imageId);
		em.persist(post);
		return post;
	}
	
	@Override
	public List<Post> findAll(final int limit, final int offset, final String sort, final String time) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p " + getJoinBySort(sort) + " where p.date > :time group by p " + getOrderBySort(sort), Post.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List<Post> findByGroup(final Group group, final int limit, final int offset, final String sort, final String time) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p " + getJoinBySort(sort) + " where p.date > :time and p.group = :group group by p " + getOrderBySort(sort), Post.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
		query.setParameter("group", group);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List<Post> findByUser(final User user, final int limit, final int offset, final String sort, final String time) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p " + getJoinBySort(sort) + " where p.date > :time and p.owner = :user group by p " + getOrderBySort(sort), Post.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
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
	public List<Post> findBySubscriptions(final User user, final int limit, final int offset, final String sort, final String time) {
		final TypedQuery<Post> query = em.createQuery("select p from Post as p join p.group.subscribedUsers as s " + getJoinBySort(sort) + " where p.date > :time and s = :user group by p " + getOrderBySort(sort), Post.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
		query.setParameter("user", user);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public int findAllCount(final String time) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p where p.date > :time", Long.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
		return query.getSingleResult().intValue();
	}

	@Override
	public int findByGroupCount(final Group group, final String time) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p where p.date > :time and p.group = :group", Long.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
		query.setParameter("group", group);
		return query.getSingleResult().intValue();
	}

	@Override
	public int findByUserCount(final User user, final String time) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p where p.date > :time and p.owner = :user", Long.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
		query.setParameter("user", user);
		return query.getSingleResult().intValue();
	}

	@Override
	public int findBySubscriptionsCount(final User user, final String time) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Post as p join p.group.subscribedUsers as su where p.date > :time and su = :user", Long.class);
		query.setParameter("time", getLocalDateTimeByTime(time));
		query.setParameter("user", user);
		return query.getSingleResult().intValue();
	}

	@Override
	public void delete(final Post post) {
		final Post p = em.merge(post);
		em.remove(p);
	}
	
	private String getJoinBySort(final String sort) {
		if (sort == null || sort.equals("new"))
			return "";
		
		if (sort.equals("top"))
			return "left join p.votesSet as v";
		
		if (sort.equals("controversial"))
			return "left join p.commentsSet as c";
		
		return "";
	}
	
	private LocalDateTime getLocalDateTimeByTime(final String time) {
		if (time == null || time.equals("all"))
			return LocalDateTime.of(0, 1, 1, 0, 0);
		
		if (time.equals("lasthour")) {
			return LocalDateTime.now().minusHours(1);
		}
		
		if (time.equals("lastday")) {
			return LocalDateTime.now().minusDays(1);
		}
		
		if (time.equals("lastweek")) {
			return LocalDateTime.now().minusWeeks(1);
		}
		
		if (time.equals("lastmonth")) {
			return LocalDateTime.now().minusMonths(1);
		}
		
		if (time.equals("lastyear")) {
			return LocalDateTime.now().minusYears(1);
		}
		
		return LocalDateTime.of(0, 0, 0, 0, 0);
	}
	
	private String getOrderBySort(final String sort) {
		if (sort == null || sort.equals("new"))
			return "order by p.date desc";
		
		if (sort.equals("top"))
			return "order by coalesce(sum(v.value), 0) desc";
		
		if (sort.equals("controversial"))
			return "order by count(*) desc";
		
		return "order by p.date desc";
	}

}
