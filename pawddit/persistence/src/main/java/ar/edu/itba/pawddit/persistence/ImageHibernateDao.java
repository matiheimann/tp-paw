package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Image;

@Repository
public class ImageHibernateDao implements ImageDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public String saveImage(final byte[] image, final String token) {
		final Image img = new Image(image, token);
		em.persist(img);
		return img.getToken();
	}

	@Override
	public Optional<byte[]> findByToken(final String token) {
		final TypedQuery<byte[]> query = em.createQuery("select byteArray from Image as i where i.token = :token", byte[].class);
		query.setParameter("token", token);
		return query.getResultList().stream().findFirst();
	}

}
