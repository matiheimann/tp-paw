package ar.edu.itba.pawddit.services;

import java.util.Optional;

import ar.edu.itba.pawddit.model.User;

public interface UserService {
	
	public Optional<User> findById(long id);
	public User create(String username, String password, String email, int score);
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
	
}
