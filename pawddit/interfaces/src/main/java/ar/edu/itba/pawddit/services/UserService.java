package ar.edu.itba.pawddit.services;

import java.util.Optional;

import ar.edu.itba.pawddit.model.User;

public interface UserService {
	public Optional<User> findById(final long id);
	public User create(final String username);
}
