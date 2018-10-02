package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

public interface UserDao {
	
	public Optional<User> findById(long id);
	public User create(String username, String password, String email);
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
	public VerificationToken createToken(User user);
	public Optional<VerificationToken> findToken(String token);
	public int deleteToken(String token);
	public int enableUser(User user);
	
}
