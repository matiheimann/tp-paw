package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VotePost;

@Repository
public class PostVoteHibernateDao implements PostVoteDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void changeVote(final User user, final Post post) {
		final VotePost vp = find(user, post).get();
		vp.setValue(-1 * vp.getValue());
		
	}

	@Override
	public void cancelVote(final User user, final Post post) {
		final VotePost vp = find(user, post).get();
		em.remove(vp);
	}

	@Override
	public VotePost votePost(final User user, final Post post, final Integer value) {
		final VotePost votePost = new VotePost(user, post, value);
		em.persist(votePost);
		return votePost;
	}

	@Override
	public int checkVote(final User user, final Post post) {
		final Optional<VotePost> votePost = find(user, post);
		if (votePost.isPresent()) {
			return votePost.get().getValue();
		}
		
		return 0;
	}
	
	private Optional<VotePost> find(final User user, final Post post) {
		final TypedQuery<VotePost> query = em.createQuery("from VotePost as vp where vp.pk.user = :user and vp.pk.post = :post", VotePost.class);
		query.setParameter("user", user);
		query.setParameter("post", post);
		return query.getResultList().stream().findFirst();
	}

}
