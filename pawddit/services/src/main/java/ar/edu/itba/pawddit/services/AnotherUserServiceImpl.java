package ar.edu.itba.pawddit.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.model.User;

@Service
public class AnotherUserServiceImpl implements UserService {

	@Override
	public Optional<User> findById(final long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User create(final String username, String password, String email, int score) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
