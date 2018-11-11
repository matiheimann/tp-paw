package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VoteComment;

@Repository
public class CommentVoteHibernateDao implements CommentVoteDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public VoteComment voteComment(User user, Comment comment, Integer value) {
		final VoteComment voteComment = new VoteComment(user, comment, value);
		em.persist(voteComment);
		return voteComment;
	}

	@Override
	public void changeVote(User user, Comment comment) {
		final VoteComment vc = find(user, comment).get();
		vc.setValue(vc.getValue() * -1);
	}

	@Override
	public void cancelVote(User user, Comment comment) {
		final VoteComment vc = find(user, comment).get();
		em.remove(vc);
	}

	@Override
	public int checkVote(User user, Comment comment) {
		final Optional<VoteComment> vc = find(user, comment);
		if(vc.isPresent()) {
			return vc.get().getValue();
		}
		
		return 0;
	}
	
	private Optional<VoteComment> find(final User user, final Comment comment) {
		final TypedQuery<VoteComment> query = em.createQuery("from VoteComment as vc where vc.pk.user = :user and vc.pk.comment = :comment", VoteComment.class);
		query.setParameter("user", user);
		query.setParameter("comment", comment);
		return query.getResultList().stream().findFirst();
	}

}
