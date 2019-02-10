package ar.edu.itba.pawddit.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@Repository
public class CommentHibernateDao implements CommentDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Comment create(final String content, final Post post, final Comment replyTo, final User user, final LocalDateTime date) {
		final Comment comment = new Comment(content, post, replyTo, user, date);
		em.persist(comment);
		return comment;
	}

	@Override
	public List<Comment> findByUser(final User user, final int limit, final int offset) {
		final TypedQuery<Comment> query = em.createQuery("from Comment as c where c.owner = :user order by c.date desc, c.commentid", Comment.class);
		query.setParameter("user", user);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<Comment> findByPost(Post post, int limit, int offset) {
		final TypedQuery<Comment> query = em.createQuery("from Comment as c where c.post = :post order by c.date desc, c.commentid", Comment.class);
		query.setParameter("post", post);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List<Comment> findByPostNoReply(final Post post, final int limit, final int offset) {
		final TypedQuery<Comment> query = em.createQuery("from Comment as c where c.post = :post and c.replyTo = null order by c.date desc, c.commentid", Comment.class);
		query.setParameter("post", post);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public Optional<Comment> findById(final Post post, final long id) {
		final TypedQuery<Comment> query = em.createQuery("from Comment as c where c.post = :post and c.commentid = :id", Comment.class);
		query.setParameter("post", post);
		query.setParameter("id", id);
		return query.getResultList().stream().findFirst();
	}

	@Override
	public int findByUserCount(final User user) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Comment as c where c.owner = :user", Long.class);
		query.setParameter("user", user);
		return query.getSingleResult().intValue();
	}
	
	@Override
	public int findByPostNoReplyCount(final Post post) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Comment as c where c.post = :post and c.replyTo = null", Long.class);
		query.setParameter("post", post);
		return query.getSingleResult().intValue();
	}

	@Override
	public int findByPostCount(final Post post) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Comment as c where c.post = :post", Long.class);
		query.setParameter("post", post);
		return query.getSingleResult().intValue();
	}
	
	@Override
	public List<Comment> findRepliesByComment(final Comment comment, final int limit, final int offset) {
		final TypedQuery<Comment> query = em.createQuery("from Comment as c where c.replyTo = :comment order by c.date desc, c.commentid", Comment.class);
		query.setParameter("comment", comment);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public int findRepliesByCommentCount(final Comment comment) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Comment as c where c.replyTo = :comment", Long.class);
		query.setParameter("comment", comment);
		return query.getSingleResult().intValue();
	}

	@Override
	public void delete(final Comment comment) {
		final Comment c = em.merge(comment);
		final TypedQuery<Comment> query = em.createQuery("from Comment as c where c.replyTo = :comment", Comment.class);
		query.setParameter("comment", comment);
		final List<Comment> comments = query.getResultList();
		for (final Comment commentReply : comments) {
			final Comment cr = em.merge(commentReply);
			cr.setReplyTo(null);
		}
		em.remove(c);
	}

}
