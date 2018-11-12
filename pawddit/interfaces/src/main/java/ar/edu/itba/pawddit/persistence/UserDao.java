package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

public interface UserDao {
	
	public Optional<User> findById(long id);
	public User create(String username, String password, String email, boolean admin, boolean enabled);
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
	public VerificationToken createToken(User user);
	public Optional<VerificationToken> findToken(String token);
	public void deleteToken(VerificationToken token);
	public User enableUser(User user);
	public void deleteUser(User user);
	public void changeData(User user, String username, String password, String email, String image);
	
}
