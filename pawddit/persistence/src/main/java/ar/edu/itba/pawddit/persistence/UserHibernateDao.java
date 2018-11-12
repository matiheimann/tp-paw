package ar.edu.itba.pawddit.persistence;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

@Repository
public class UserHibernateDao implements UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<User> findById(final long id) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.userid = :userid", User.class);
		query.setParameter("userid", id);
		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public User create(final String username, final String password, final String email, final boolean admin, final boolean enabled) {
		final User user = new User(username, password, email, admin, enabled);
		em.persist(user);
		return user;
	}
	
	@Override
	public Optional<User> findByUsername(final String username) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class);
		query.setParameter("username", username);
		return query.getResultList().stream().findFirst();
	}

	@Override
	public Optional<User> findByEmail(final String email) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.email = :email", User.class);
		query.setParameter("email", email);
		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public VerificationToken createToken(final User user) {
		final String token = UUID.randomUUID().toString();
		final VerificationToken vt = new VerificationToken(token, user);
		em.persist(vt);
		return vt;
	}
	
	@Override
	public Optional<VerificationToken> findToken(final String token) {
		final TypedQuery<VerificationToken> query = em.createQuery("from VerificationToken as vt where vt.token = :token", VerificationToken.class);
		query.setParameter("token", token);
		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public void deleteToken(final VerificationToken token) {
		final VerificationToken vt = em.merge(token);
		em.remove(vt);
	}
	
	@Override
	public User enableUser(final User user) {
		final User u = em.merge(user);
		u.setEnabled(true);
		return u;
	}
	
	@Override
	public void deleteUser(final User user) {
		final User u = em.merge(user);
		em.remove(u);
	}
	
	@Override
	public void changeData(User user, String username, String password, String email, String image) {
		final User u = em.merge(user);
		u.setUsername(username);
		u.setPassword(password);
		u.setEmail(email);
		u.setImageid(image);
	}
}
