package ar.edu.itba.pawddit.services;

import java.util.Optional;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

public interface UserService {
	
	public Optional<User> findById(long id);
	public User create(String username, String password, String email, Boolean byAdmin);
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
	public VerificationToken createToken(User user);
	public Optional<VerificationToken> findToken(String token);
	public void enableUser(User user);
	public void deleteUser(User user);
	public void changeData(User user, String username, String password, String email);
	
}
